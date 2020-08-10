package com.rh.note.build;

import com.rh.note.model.file.Config;
import com.rh.note.model.file.Content;
import com.rh.note.model.file.ProjectInfo;
import com.rh.note.model.file.ReadMe;
import com.rh.note.model.file.TwoLevel;

/**
 * 项目信息
 */
public interface ProjectBuild {

    ProjectInfo pi = new ProjectInfo();

    static String getAbsolutePath(){
        return pi.getAbsolutePath();
    }

    static void setAbsolutePath(String absolutePath) {
        pi.setAbsolutePath(absolutePath);
    }

    static String getProjectName() {
        return pi.getProjectName();
    }

    static void setProjectName(String projectName) {
        pi.setProjectName(projectName);
    }

    static ReadMe getReadMe() {
        return pi.getReadMe();
    }

    static void setReadMe(ReadMe readMe) {
        pi.setReadMe(readMe);
    }

    static Config getConfig() {
        return pi.getConfig();
    }

    static void setConfig(Config config) {
        pi.setConfig(config);
    }

    static TwoLevel getTwoLevel() {
        return pi.getTwoLevel();
    }

    static void setTwoLevel(TwoLevel twoLevel) {
        pi.setTwoLevel(twoLevel);
    }

    static Content getContent() {
        return pi.getContent();
    }

    static void setContent(Content content) {
        pi.setContent(content);
    }


}
