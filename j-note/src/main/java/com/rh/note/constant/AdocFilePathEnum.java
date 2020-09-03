package com.rh.note.constant;

import com.rh.note.common.IAdocFile;
import lombok.NonNull;

import java.io.File;

/**
 * adoc文件目录
 */
public enum AdocFilePathEnum {

    read_me_file(){
        @Override
        public String getAdocFilePath(@NonNull String projectPath) {
            return projectPath + File.separator + "README.adoc"; // /home/hang/my_program/adoc_project/README.adoc
        }
    },
    two_level_folder() {
        @Override
        public String getAdocFilePath(@NonNull String projectPath) {
            return String.format("%s%sadoc%stwoLevel%s", projectPath, File.separator, File.separator, File.separator); // /home/hang/my_program/adoc_project/adoc/twoLevel/
        }
    },
    content() {
        @Override
        public String getAdocFilePath(@NonNull String projectPath) {
            return String.format("%s%sadoc%scontent%s", projectPath, File.separator, File.separator, File.separator); // /home/hang/my_program/adoc_project/adoc/content/
        }
    },
    ;

    AdocFilePathEnum() {
    }

    /**
     * 通过文件地址获得空的adocFile对象
     */
    public static IAdocFile getAdocFile(String filePath) {
        //todo
        return null;
    }

    public String getAdocFilePath(@NonNull String projectPath) {
        return "";
    }

}
