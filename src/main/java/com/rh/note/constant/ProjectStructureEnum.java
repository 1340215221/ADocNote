package com.rh.note.constant;

import com.rh.note.common.IAdocFile;
import com.rh.note.exception.AdocException;
import com.rh.note.file.AdocFile;
import com.rh.note.file.ConfigFile;
import com.rh.note.file.ContentFile;
import com.rh.note.file.ReadMeFile;
import com.rh.note.file.TwoLevelFile;
import com.rh.note.util.BaseEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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
        return matchInstance(filePath).newAdocFile().setFilePath(filePath);
    }

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
        return null;
    }
}
