package com.ishow.plugin.insertcode.configure;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by yuhaiyang on 2017/6/28.
 * MVP模版
 */
public class MvpTemplateConfigure implements SearchableConfigurable {

    private MvpTemplateForm mForm;
    private MvpTemplateSetting mSettings;

    public MvpTemplateConfigure() {
        mSettings = MvpTemplateSetting.getInstance();
    }

    @NotNull
    @Override
    public String getId() {
        return MvpTemplateConfigure.class.getName();
    }

    @Nls
    public String getDisplayName() {
        return MvpTemplateConfigure.class.getSimpleName();
    }

    @Nullable
    public JComponent createComponent() {
        if (null == mForm) {
            mForm = new MvpTemplateForm();
        }

        return mForm.mainPanel;
    }

    @Override
    public void reset() {
        mForm.contractTemplate.setText(mSettings.getContractTemplate());
        mForm.presenterTemplate.setText(mSettings.getPresenterTemplate());
        mForm.viewTemplate.setText(mSettings.getViewTemplate());
    }

    @Override
    public boolean isModified() {

        return !mSettings.getContractTemplate().equals(mForm.contractTemplate.getText())
                || !mSettings.getPresenterTemplate().equals(mForm.presenterTemplate.getText())
                || !mSettings.getViewTemplate().equals(mForm.viewTemplate.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        mSettings.setContractTemplate(mForm.contractTemplate.getText());
        mSettings.setPresenterTemplate(mForm.presenterTemplate.getText());
        mSettings.setViewTemplate(mForm.viewTemplate.getText());
    }

}
