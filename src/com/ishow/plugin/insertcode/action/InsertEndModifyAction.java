package com.ishow.plugin.insertcode.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.ishow.plugin.insertcode.configure.InsertAnnotationsSetting;
import com.ishow.plugin.insertcode.utils.InsertCodeUtils;
import com.ishow.plugin.insertcode.utils.StringUtils;

/**
 * Created by yuhaiyang on 2017/6/28.
 */
public class InsertEndModifyAction extends BaseGenerateAction {

    public InsertEndModifyAction() {
        super(null);
    }

    public InsertEndModifyAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        InsertAnnotationsSetting setting = InsertAnnotationsSetting.getInstance();
        String message = setting.getEndAnnotations();
        // 获取编辑器中的文件
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor editor = event.getData(PlatformDataKeys.EDITOR);
        PsiFile file = PsiUtilBase.getPsiFileInEditor(editor, project);
        PsiClass targetClass = getTargetClass(editor, file);;


        InsertCodeUtils.insertCode(event, StringUtils.format(message));
    }
}
