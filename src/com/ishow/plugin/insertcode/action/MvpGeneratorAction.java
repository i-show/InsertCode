package com.ishow.plugin.insertcode.action;

import com.intellij.ide.IdeView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.ishow.plugin.insertcode.configure.MvpTemplateSetting;
import com.ishow.plugin.insertcode.template.MVPTemplate;
import com.ishow.plugin.insertcode.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yuhaiyang on 2017/6/28.
 * MVP代码生成
 */
public class MvpGeneratorAction extends AnAction {
    private Project mProject;
    private PsiDirectory mPsiDirectory;

    public void actionPerformed(AnActionEvent event) {
        final IdeView view = event.getRequiredData(LangDataKeys.IDE_VIEW);
        mPsiDirectory = view.getOrChooseDirectory();

        mProject = event.getData(PlatformDataKeys.PROJECT);
        String path = getCurrentPath(event);
        String moudleName = inputModuleName(mProject);
        try {
            create(path, moudleName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Messages.showMessageDialog("文件已经生成完毕", "提示", Messages.getInformationIcon());
        refreshProject(event);
    }


    private String inputModuleName(Project project) {
        return Messages.showInputDialog(project,
                "请输入模块名称。", "输入模块名称",
                Messages.getQuestionIcon());
    }


    private void refreshProject(AnActionEvent e) {
        e.getProject().getBaseDir().refresh(false, true);
    }


    private void create(String pathString, String targetName) throws IOException {
        final String packageName = getPackageName(pathString);
        final MvpTemplateSetting setting = MvpTemplateSetting.getInstance();
        createContract(setting, pathString, packageName, targetName);
        createPresenter(setting, pathString, packageName, targetName);
        createView(setting, pathString, packageName, targetName);
    }

    private void createContract(MvpTemplateSetting setting, String pathString, String packageName, String targetName) throws IOException {
        final String fileName = StringUtils.plusString(targetName, MVPTemplate.CONTRACT_SUFFIX, ".java");
        String content = setting.getContractTemplate().replaceAll("\\$\\{PACKAGE_NAME}", packageName);
        content = content.replaceAll("\\$\\{TARGET_NAME}", targetName);
        content = StringUtils.format(content);
        createFile(pathString, fileName, content);
    }

    private void createPresenter(MvpTemplateSetting setting, String pathString, String packageName, String targetName) throws IOException {
        final String fileName = StringUtils.plusString(targetName, MVPTemplate.PERSENTER_SUFFIX, ".java");
        String content = setting.getPresenterTemplate().replaceAll("\\$\\{PACKAGE_NAME}", packageName);
        content = content.replaceAll("\\$\\{TARGET_NAME}", targetName);
        content = StringUtils.format(content);
        createFile(pathString, fileName, content);
    }

    private void createView(MvpTemplateSetting setting, String pathString, String packageName, String targetName) throws IOException {
        final String fileName = StringUtils.plusString(targetName, MVPTemplate.VIEW_SUFFIX, ".java");
        String content = setting.getViewTemplate().replaceAll("\\$\\{PACKAGE_NAME}", packageName);
        content = content.replaceAll("\\$\\{TARGET_NAME}", targetName);
        content = StringUtils.format(content);
        createFile(pathString, fileName, content);
    }

    private void createFile(String pathString, String fileName, String content) throws IOException {
        File path = new File(pathString);
        if (path.isFile()) {
            path = path.getParentFile();
        }

        File file = new File(path, fileName);
        if (!path.exists()) {
            //noinspection ResultOfMethodCallIgnored
            path.mkdirs();
        }

        if (file.exists()) {
            Messages.showMessageDialog("文件已经存在", "提示", Messages.getInformationIcon());
            return;
        }

        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content);
        writer.flush();
        writer.close();
    }

    private String getPackageName(String path) {
        String[] strings = path.split("/");
        StringBuilder packageName = new StringBuilder();
        boolean packageBegin = false;
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            if ((string.equals("com")) || (string.equals("org")) || (string.equals("cn")) || (string.equals("pw"))) {
                packageBegin = true;
            }
            if (packageBegin) {
                packageName.append(string);
                if (i != strings.length - 1) {
                    packageName.append(".");
                }
            }
        }
        return packageName.toString();
    }

    private String getCurrentPath(AnActionEvent e) {
        VirtualFile currentFile = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        if (currentFile != null) {
            return currentFile.getPath();
        }
        return null;
    }
}
