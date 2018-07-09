package com.ishow.plugin.insertcode.template;

/**
 * Created by yuhaiyang on 2017/6/28.
 * MVP模式
 */
public class MVPTemplate {

    public static final String CONTRACT_SUFFIX = "Contract";

    public static final String CONTRACT =
            "package ${PACKAGE_NAME};\n" +
                    "\n" +
                    "/**\n" +
                    "* Created by ${USER} on ${DATE}.\n" +
                    "*/\n" +
                    "interface ${TARGET_NAME}" + CONTRACT_SUFFIX + "{\n" +
                    "\n" +
                    "    interface View extends BaseView, IViewStatus {\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "    interface Presenter extends BasePresenter {\n" +
                    "\n" +
                    "    }\n" +
                    "}";

    public static final String PERSENTER_SUFFIX = "Presenter";
    public static final String PERSENTER =
            "package ${PACKAGE_NAME};\n" +
                    "\n" +
                    "/**\n" +
                    "* Created by ${USER} on ${DATE}.\n" +
                    "*/\n" +
                    "class ${TARGET_NAME}" + PERSENTER_SUFFIX + " implements ${TARGET_NAME}Contract.Presenter {\n" +
                    "   \n" +
                    "    private ${TARGET_NAME}Contract.View mView;\n" +
                    "\n" +
                    "    ${TARGET_NAME}Presenter(${TARGET_NAME}Contract.View view) {\n" +
                    "        mView = view;\n" +
                    "    }\n" +
                    "   \n" +
                    "}";


    public static final String VIEW_SUFFIX = "Activity";
    public static final String VIEW =
            "package ${PACKAGE_NAME};\n" +
                    "\n" +
                    "/**\n" +
                    "* Created by ${USER} on ${DATE}.\n" +
                    "*/\n" +
                    "public class ${TARGET_NAME}" + VIEW_SUFFIX + " extends AppBaseActivity implements ${TARGET_NAME}Contract.View {\n" +
                    "\n" +
                    "    private ${TARGET_NAME}Contract.Presenter mPresenter;\n" +
                    "    @Override\n" +
                    "    protected void onCreate(Bundle savedInstanceState) {\n" +
                    "        super.onCreate(savedInstanceState);\n" +
                    "        mPresenter = new ${TARGET_NAME}Presenter(this);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    protected void initViews() {\n" +
                    "        super.initViews();\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "}";
}
