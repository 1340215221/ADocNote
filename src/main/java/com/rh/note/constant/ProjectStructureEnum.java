package com.rh.note.constant;

import com.rh.note.common.IAdocFile;
import com.rh.note.exception.AdocException;
import com.rh.note.file.ConfigFile;
import com.rh.note.file.ContentFile;
import com.rh.note.file.ReadMeFile;
import com.rh.note.file.TwoLevelFile;
import com.rh.note.util.BaseEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.text.FieldPosition;
import java.util.Arrays;

/**
 * 项目结构
 */
@Getter
public enum ProjectStructureEnum implements BaseEnum {

    /**
     * read-me
     */
    read_me(){
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.endsWith("readme.adoc") || filePath.endsWith("README.adoc");
        }

        @Override
        public String getChildrenPath() {
            return "adoc/twoLevel/";
        }

        @Override
        protected IAdocFile newAdocFile() {
            return new ReadMeFile();
        }

        @Override
        protected String includePathToFilePath(String filePath) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }

        @Override
        public String generateTargetPathForInclude(String targetFilePath) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }
    },
    /**
     * global-config
     */
    global_config() {
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.endsWith("config.adoc");
        }

        @Override
        protected IAdocFile newAdocFile() {
            return new ConfigFile();
        }

        @Override
        protected String includePathToFilePath(String filePath) {
            return "config.adoc";
        }

        @Override
        public String generateTargetPathForInclude(String targetFilePath) {
            return "config.adoc";
        }
    },
    /**
     * two-level
     */
    two_level() {
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.contains("twoLevel");
        }

        @Override
        public String getChildrenPath() {
            return "adoc/content/";
        }

        @Override
        protected IAdocFile newAdocFile() {
            return new TwoLevelFile();
        }

        @Override
        protected String includePathToFilePath(String filePath) {
            return filePath;
        }

        @Override
        public String generateTargetPathForInclude(String targetFilePath) {
            return targetFilePath;
        }
    },
    /**
     * content
     */
    content() {
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.contains("content");
        }

        @Override
        protected IAdocFile newAdocFile() {
            return new ContentFile();
        }

        @Override
        protected String includePathToFilePath(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                throw new AdocException(ErrorMessage.PARAMETER_ERROR);
            }
            return "adoc" + filePath.substring(2);
        }

        @Override
        public String generateTargetPathForInclude(String targetFilePath) {
            if (StringUtils.isBlank(targetFilePath)) {
                throw new AdocException(ErrorMessage.PARAMETER_ERROR);
            }
            return ".." + targetFilePath.substring(4);
        }
    },
    ;

    protected abstract IAdocFile newAdocFile();

    /**
     * 配置实例，通过文件地址
     */
    public abstract boolean match(String filePath);

    /**
     * 构建简单的adoc文件对象
     */
    public static IAdocFile buildSimpleAdocFile(String filePath) {
        ProjectStructureEnum projectStructureEnum = matchInstance(filePath);
        return projectStructureEnum.newAdocFile().setFilePath(projectStructureEnum.includePathToFilePath(filePath));
    }

    /**
     * 处理相对路径为项目路径
     */
    protected abstract String includePathToFilePath(String filePath);

    /**
     * 获得实例
     */
    public static ProjectStructureEnum getInstance(String name) {
        return BaseEnum.getInstance(name);
    }

    /**
     * 匹配实例，通过文件地址
     */
    public static ProjectStructureEnum matchInstance(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }
        return Arrays.stream(values())
                .filter(e -> e.match(filePath))
                .findFirst()
                .orElseThrow(() -> new AdocException(ErrorMessage.PARAMETER_ERROR));
    }

    /**
     * 获得子文件目录
     */
    public String getChildrenPath() {
        //todo
        return null;
    }

    /**
     * 生成include中的相对地址
     */
    public static String generateIncludeTargetPath(String targetFilePath) {
        return matchInstance(targetFilePath).generateTargetPathForInclude(targetFilePath);
    }

    /**
     * 生成include中的相对地址
     */
    public abstract String generateTargetPathForInclude(String targetFilePath);
}
