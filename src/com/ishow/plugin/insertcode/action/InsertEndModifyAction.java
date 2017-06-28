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
        InsertCodeUtils.insertCode(event, StringUtils.format(message));
    }
}
