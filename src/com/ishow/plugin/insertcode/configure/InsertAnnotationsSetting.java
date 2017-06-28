package com.ishow.plugin.insertcode.configure;

/**
 * Created by admin on 2016/12/18.
 */

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;


@State(
        name = "InsertAnnotationsSetting",
        storages = {@Storage(
                id = "other",
                file = "$APP_CONFIG$/format.xml"
        )}
)
public class InsertAnnotationsSetting implements PersistentStateComponent<Element> {

    private String startAnnotations;
    private String endAnnotations;

    public InsertAnnotationsSetting() {
    }

    public static InsertAnnotationsSetting getInstance() {
        return ServiceManager.getService(InsertAnnotationsSetting.class);
    }

    @Nullable
    public Element getState() {
        Element element = new Element("InsertAnnotationsSetting");
        element.setAttribute("startAnnotations", getStartAnnotations());
        element.setAttribute("endAnnotations", getEndAnnotations());
        return element;
    }

    public void loadState(Element state) {
        setStartAnnotations(state.getAttributeValue("startAnnotations"));
        setEndAnnotations(state.getAttributeValue("endAnnotations"));
    }

    public String getStartAnnotations() {
        return this.startAnnotations == null ? InsertAnnotationsConfigure.DEFAULT_START_ANNOTATION_TEMP : this.startAnnotations;
    }

    void setStartAnnotations(String start) {
        startAnnotations = start;
    }

    public String getEndAnnotations() {
        return endAnnotations == null ? InsertAnnotationsConfigure.DEFAULT_END_ANNOTATION_TEMP : endAnnotations;
    }

    void setEndAnnotations(String end) {
        endAnnotations = end;
    }

}
