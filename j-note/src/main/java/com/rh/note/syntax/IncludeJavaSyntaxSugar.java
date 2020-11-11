package com.rh.note.syntax;

import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.constants.BaseConstants;
import com.rh.note.file.JavaProConfig;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入java 语法
 */
@Data
public class IncludeJavaSyntaxSugar {

    private static final String regex = "^(\\s*)=>j.(?:(" + BaseConstants.pro_label_regex + ")(?:(\\s+)(" + BaseConstants.package_path_regex + "))?)?\\s*$";
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
    /**
     * 分隔符是否存在
     */
    private boolean existSeparator = false;

    public @Nullable IncludeJavaSyntaxSugar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indented = matcher.group(1);
        proLabel = matcher.group(2);
        existSeparator = Optional.ofNullable(matcher.group(3))
                .filter(str -> str.length() > 0)
                .isPresent();
        packagePath = matcher.group(4);
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
        if (StringUtils.isNotBlank(proLabel) && existSeparator) {
            return NeedPromptType.FILE_PATH;
        }
        if (!existSeparator) {
            return NeedPromptType.PRO_LIST;
        }
        return NeedPromptType.NOTHING;
    }

    public IncludeSyntax copyToByFilePath(String filePath) {
        IncludeSyntax syntax = new IncludeSyntax();
        syntax.setIndented(indented);
        syntax.setTargetRelativePath(getTargetRelativePath(filePath));
        syntax.setTargetFileName(getTargetRelativeName());
        syntax.setTargetFileSuf("java");
        return syntax;
    }

    /**
     * todo
     * 获得java相对路径
     */
    private String getTargetRelativePath(String filePath) {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(proLabel) || StringUtils.isBlank(packagePath)) {
            return null;
        }
        AdocFileTypeEnum fileType = AdocFileTypeEnum.matchByFilePath(filePath);
        if (fileType == null) {
            return null;
        }
        String relativePath = fileType.getJavaRelativePath();
        String proPath = new JavaProConfig().getProPath(proLabel);
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        if (!packagePath.endsWith(".java")) {
            return null;
        }
        int indexJava = packagePath.lastIndexOf(".java");
        String strTemp = packagePath.substring(0, indexJava);
        if (StringUtils.isBlank(strTemp)) {
            return null;
        }
        int packageEndIndex = strTemp.lastIndexOf('.');
        String package_path = "";
        if (packageEndIndex > 0) {
            package_path = strTemp.substring(0, packageEndIndex + 1).replaceAll("\\.", "/");
        }
        return relativePath + proPath + package_path + getTargetRelativeName() + ".java";
    }

    /**
     * todo
     * 指向文件名
     */
    private String getTargetRelativeName() {
        if (StringUtils.isBlank(packagePath)) {
            return null;
        }
        int indexJava = packagePath.lastIndexOf(".java");
        if (indexJava < 0) {
            return null;
        }
        String strTemp = packagePath.substring(0, indexJava);
        if (StringUtils.isBlank(strTemp)) {
            return null;
        }
        int index = strTemp.lastIndexOf('.');
        if (index < 0) {
            return null;
        }
        return strTemp.substring(index + 1);
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
