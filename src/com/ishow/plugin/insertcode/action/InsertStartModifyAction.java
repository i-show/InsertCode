package com.ishow.plugin.insertcode.action;

import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.generation.actions.BaseGenerateAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.ishow.plugin.insertcode.configure.InsertAnnotationsSetting;
import com.ishow.plugin.insertcode.utils.InsertCodeUtils;
import com.ishow.plugin.insertcode.utils.StringUtils;

/**
 * Created by yuhaiyang on 2017/6/27.
 * 插入开始修改
 */
public class InsertStartModifyAction extends BaseGenerateAction {
    public InsertStartModifyAction() {
        super(null);
    }

    public InsertStartModifyAction(CodeInsightActionHandler handler) {
        super(handler);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        InsertAnnotationsSetting setting = InsertAnnotationsSetting.getInstance();
        String message = setting.getStartAnnotations();
        InsertCodeUtils.insertCode(e, StringUtils.format(message));
    }
}
