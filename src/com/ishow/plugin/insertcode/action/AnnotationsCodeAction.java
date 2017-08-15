package com.ishow.plugin.insertcode.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.ishow.plugin.insertcode.utils.ReplaceCodeUtils;

/**
 * Created by yuhaiyang on 2017/8/14.
 */
public class AnnotationsCodeAction extends BaseGenerateAction {

    public AnnotationsCodeAction() {
        super(null);
    }

    public AnnotationsCodeAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        ReplaceCodeUtils.annotationsCode(event);
    }
}
