package com.ishow.plugin.insertcode.configure;

/**
 * Created by admin on 2016/12/18.
 */

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.ishow.plugin.insertcode.template.MVPTemplate;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;


@State(
        name = "MvpTemplateSetting",
        storages = {@Storage(
                id = "other",
                file = "$APP_CONFIG$/format.xml"
        )}
)
public class MvpTemplateSetting implements PersistentStateComponent<Element> {

    private String contractTemplate;
    private String presenterTemplate;
    private String viewTemplate;

    public MvpTemplateSetting() {
    }

    public static MvpTemplateSetting getInstance() {
        return ServiceManager.getService(MvpTemplateSetting.class);
    }

    @Nullable
    public Element getState() {
        Element element = new Element("MvpTemplateSetting");
        element.setAttribute("contractTemplate", getContractTemplate());
        element.setAttribute("presenterTemplate", getPresenterTemplate());
        element.setAttribute("viewTemplate", getViewTemplate());
        return element;
    }

    public void loadState(Element state) {
        setContractTemplate(state.getAttributeValue("contractTemplate"));
        setPresenterTemplate(state.getAttributeValue("presenterTemplate"));
        setViewTemplate(state.getAttributeValue("viewTemplate"));
    }


    public String getContractTemplate() {
        return contractTemplate == null ? MVPTemplate.CONTRACT : contractTemplate;
    }

    void setContractTemplate(String contractTemplate) {
        this.contractTemplate = contractTemplate;
    }

    public String getPresenterTemplate() {
        return presenterTemplate == null ? MVPTemplate.PERSENTER : presenterTemplate;
    }

    void setPresenterTemplate(String presenterTemplate) {
        this.presenterTemplate = presenterTemplate;
    }

    public String getViewTemplate() {
        return viewTemplate == null ? MVPTemplate.VIEW : viewTemplate;
    }

    void setViewTemplate(String viewTemplate) {
        this.viewTemplate = viewTemplate;
    }
}
