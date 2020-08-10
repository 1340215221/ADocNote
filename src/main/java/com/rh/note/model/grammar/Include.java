package com.rh.note.model.grammar;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
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
}
