package com.rh.note.syntax;

import com.rh.note.constants.BaseConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入java 语法
 */
@Data
public class IncludeJavaSyntax {

    private static final String regex = "^(\\s*)=>j.(?:(" + BaseConstants.pro_label_regex + ") (?:(" + BaseConstants.package_path_regex + "))?)?\\s*$";
    /**
     * 缩进
     */
    private String indented;
    /**
     * 项目标识
     */
    private String proLabel;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 需要提示的类型
     */
    private NeedPromptType type;

    public @Nullable IncludeJavaSyntax init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indented = matcher.group(1);
        proLabel = matcher.group(2);
        packagePath = matcher.group(3);
        type = parsingType();
        return this;
    }

    /**
     * 项目提示
     */
    public boolean isProPrompt() {
        return NeedPromptType.PRO_LIST.equals(type);
    }

    /**
     * 包路径提示
     */
    public boolean isPackagePrompt() {
        return NeedPromptType.FILE_PATH.equals(type);
    }

    /**
     * 判断需要提示类型
     */
    private NeedPromptType parsingType() {
        if (StringUtils.isBlank(proLabel)) {
            return NeedPromptType.PRO_LIST;
        }
        if (StringUtils.isBlank(packagePath)) {
            return NeedPromptType.FILE_PATH;
        }
        return NeedPromptType.NOTHING;
    }

    /**
     * 需要提示的类型
     */
    public enum NeedPromptType {
        /**
         * 项目列表
         */
        PRO_LIST,
        /**
         * 文件目录
         */
        FILE_PATH,
        /**
         * 每有
         */
        NOTHING,
        ;
    }

}
