package com.ishow.plugin.insertcode.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.SyntheticElement;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yuhaiyang on 2017/6/27.
 * 插入代码工具
 */
public class InsertCodeUtils extends WriteCommandAction.Simple {
    private int mStartOffset;
    private String mMessage;
    private Project mProject;
    private PsiFile mFile;
    private Editor mEditor;
    private PsiClass mTargetClass;

    private InsertCodeUtils(Project project, PsiFile... files) {
        super(project, files);
        mProject = project;
        mFile = files[0];
    }

    private void insertMessage(Editor editor, PsiClass targetClass, int startOffset, String messsage) {
        mEditor = editor;
        mStartOffset = startOffset;
        mMessage = messsage;
        mTargetClass = targetClass;
        execute();
    }

    public static void insertCode(AnActionEvent event, String messsage) {
        // 获取编辑器中的文件
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        if (editor == null || project == null) {
            return;
        }

        PsiFile file = PsiUtilBase.getPsiFileInEditor(editor, project);
        PsiClass psiClass = getTargetClass(editor, file);
        SelectionModel model = editor.getSelectionModel();

        int startOffset = model.getSelectionStart();
        
        InsertCodeUtils utils = new InsertCodeUtils(project, file);
        utils.insertMessage(editor, psiClass, startOffset, messsage);
    }


    @Override
    protected void run() throws Throwable {
        Document document = mEditor.getDocument();
        document.insertString(mStartOffset, mMessage);

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
            PsiClass target = (PsiClass) PsiTreeUtil.getParentOfType(element, PsiClass.class);
            return target instanceof SyntheticElement ? null : target;
        }
    }

}
