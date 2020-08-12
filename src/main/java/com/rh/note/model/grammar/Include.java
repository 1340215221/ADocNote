package com.rh.note.model.grammar;

import com.rh.note.constant.ProjectStructureEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class Include {

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 标题级别
     */
    private Integer level;
    /**
     * 缩进空白字符
     */
    private String indent;

    public Include init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }

        Matcher matcher = Pattern.compile("^\\s*include::([^\\.]+)\\.(\\S+)\\[\\S*\\]$").matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }

        filePath = matcher.group(1) + ".adoc";
        fileSuffix = matcher.group(2);
        return this;
    }

    /**
     * 获得主标题名字
     */
    public String getTitleName() {
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length() - 5);
    }

    /**
     * 初始化，通过当前行是否为include简单语法
     */
    public Include initByGrammar(String lineContent, String parentFilePath) {
        if (StringUtils.isBlank(lineContent) || StringUtils.isBlank(parentFilePath)) {
            return null;
        }
        Matcher matcher = Pattern.compile("^(\\s*)=>([1-9])\\s([\\u4e00-\\u9fa5a-zA-Z0-9_-]+)\\s*$").matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indent = matcher.group(1);
        level = Integer.valueOf(matcher.group(2));
        filePath = this.generateFilePath(matcher.group(3), parentFilePath);
        return this;
    }

    /**
     * 生成完整文件路径
     */
    private String generateFilePath(String fileName, String parentFilePath) {
        if (StringUtils.isBlank(fileName) || StringUtils.isBlank(parentFilePath)) {
            return null;
        }
        //todo
        ProjectStructureEnum parentStructure = ProjectStructureEnum.getInstance(parentFilePath);
        return parentStructure.getChildrenPath(parentFilePath);
    }
}
