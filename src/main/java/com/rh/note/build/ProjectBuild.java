package com.rh.note.build;

import com.rh.note.file.ConfigFile;
import com.rh.note.file.ContentFile;
import com.rh.note.file.ProjectDirectory;
import com.rh.note.file.ReadMeFile;
import com.rh.note.file.TwoLevelFile;

/**
 * 项目信息
 */
public interface ProjectBuild {

    ProjectDirectory pi = new ProjectDirectory();

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

    static ReadMeFile getReadMe() {
        return pi.getReadMe();
    }

    static void setReadMe(ReadMeFile readMe) {
        pi.setReadMe(readMe);
    }

    static ConfigFile getConfig() {
        return pi.getConfig();
    }

    static void setConfig(ConfigFile config) {
        pi.setConfig(config);
    }

    static TwoLevelFile getTwoLevel() {
        return pi.getTwoLevel();
    }

    static void setTwoLevel(TwoLevelFile twoLevel) {
        pi.setTwoLevel(twoLevel);
    }

    static ContentFile getContent() {
        return pi.getContent();
    }

    static void setContent(ContentFile content) {
        pi.setContent(content);
    }


}
