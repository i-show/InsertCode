package com.ishow.plugin.insertcode.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.ishow.plugin.insertcode.AnnotationInfo;

/**
 * Created by yuhaiyang on 2017/6/27.
 * 插入代码工具
 */
public class ReplaceCodeUtils extends WriteCommandAction.Simple {
    private static final String START = " * ";

    private Project mProject;
    private PsiFile mFile;
    private Editor mEditor;
    private PsiClass mTargetClass;
    private AnnotationInfo mInfo;

    private ReplaceCodeUtils(Project project, PsiFile... files) {
        super(project, files);
        mProject = project;
        mFile = files[0];
    }

    private void release(Editor editor, PsiClass targetClass, AnnotationInfo info) {
        mEditor = editor;
        mTargetClass = targetClass;
        mInfo = info;
        execute();
    }

    public static void annotationsCode(AnActionEvent event) {
        // 获取编辑器中的文件
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null || project == null) {
            return;
        }

        PsiFile file = PsiUtilBase.getPsiFileInEditor(editor, project);
        PsiClass psiClass = getTargetClass(editor, file);
        SelectionModel model = editor.getSelectionModel();


        String selectedMessage = model.getSelectedText();
        VisualPosition startPosition = model.getSelectionStartPosition();
        VisualPosition endPosition = model.getSelectionEndPosition();
        if (StringUtils.isEmpty(selectedMessage) || startPosition == null || endPosition == null) {
            return;
        }
        AnnotationInfo info = new AnnotationInfo();
        info.startColumn = startPosition.getColumn();
        info.startLine = startPosition.getLine();
        info.startOffset = model.getSelectionStart();
        info.endOffset = model.getSelectionEnd();
        info.endLine = endPosition.getLine();

        StringBuilder messageBuilder = new StringBuilder(selectedMessage);
        insertInfo(messageBuilder, 0, info.startColumn);
        String space = getSpace(info.startColumn);

        String header = "/*\n" + space + START;
        String ender = "\n" + space + " */";
        messageBuilder.insert(0, header);
        messageBuilder.append(ender);
        info.message = messageBuilder.toString();

        ReplaceCodeUtils utils = new ReplaceCodeUtils(project, file);
        utils.release(editor, psiClass, info);
    }

    private static void insertInfo(StringBuilder message, int startIndex, int startColumn) {
        int indexFirst = message.indexOf("\n", startIndex);
        int indexSecond = message.indexOf("\n", indexFirst + 1);
        System.out.println("indexFirst = " + indexFirst);
        System.out.println("indexSecend = " + indexSecond);
        if (indexFirst == -1) {
            return;
        }

        int lineLength = indexSecond - indexFirst;
        if (lineLength < startColumn && indexSecond != -1) {
            String space = getSpace(startColumn + 1 - lineLength);
            String start = StringUtils.plusString(space, START);
            message.insert(indexFirst + 1, start);
        } else {
            message.insert(indexFirst + 1 + startColumn, START);
        }
        insertInfo(message, indexFirst + 1 + START.length(), startColumn);

    }

    @Override
    protected void run() throws Throwable {
        Document document = mEditor.getDocument();
        document.deleteString(mInfo.startOffset, mInfo.endOffset);
        document.insertString(mInfo.startOffset, mInfo.message);

        try {
            JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(mProject);
            styleManager.optimizeImports(mFile);
            styleManager.shortenClassReferences(mTargetClass);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static PsiClass getTargetClass(Editor editor, PsiFile file) {
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        if (element == null) {
            return null;
        } else {
            PsiClass target = PsiTreeUtil.getParentOfType(element, PsiClass.class);
            return target instanceof SyntheticElement ? null : target;
        }
    }

    private static String getSpace(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }
}
