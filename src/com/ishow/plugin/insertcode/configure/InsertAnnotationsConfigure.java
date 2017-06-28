package com.ishow.plugin.insertcode.configure;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by yuhaiyang on 2017/6/28.
 */
public class InsertAnnotationsConfigure implements SearchableConfigurable {
    static final String DEFAULT_START_ANNOTATION_TEMP = "// modify by ${USER} on ${DATE} (start)";
    static final String DEFAULT_END_ANNOTATION_TEMP = "// modify by ${USER} on ${DATE} (end)";

    private InsertAnnotationsForm mForm;
    private InsertAnnotationsSetting mSettings;

    public InsertAnnotationsConfigure() {
        mSettings = InsertAnnotationsSetting.getInstance();
    }

    @NotNull
    @Override
    public String getId() {
        return InsertAnnotationsConfigure.class.getName();
    }

    @Nls
    public String getDisplayName() {
        return InsertAnnotationsConfigure.class.getSimpleName();
    }

    @Nullable
    public JComponent createComponent() {
        if (null == mForm) {
            mForm = new InsertAnnotationsForm();
        }

        return mForm.mainPanel;
    }

    @Override
    public void reset() {
        mForm.startAnnotations.setText(mSettings.getStartAnnotations());
        mForm.endAnnotations.setText(mSettings.getEndAnnotations());
    }

    @Override
    public boolean isModified() {

        return !mSettings.getStartAnnotations().equals(mForm.startAnnotations.getText())
                || !mSettings.getEndAnnotations().equals(mForm.endAnnotations.getText());
    }

    @Override
    public void apply() throws ConfigurationException {
        mSettings.setStartAnnotations(mForm.startAnnotations.getText());
        mSettings.setEndAnnotations(mForm.endAnnotations.getText());
    }

}
