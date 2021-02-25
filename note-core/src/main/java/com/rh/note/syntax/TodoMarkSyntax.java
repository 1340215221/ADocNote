package com.rh.note.syntax;

import com.rh.note.ao.SelectAndReplaceAO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 待完成标记 语法
 */
public class TodoMarkSyntax {
    /**
     * 正则
     */
    private static final String regex = "//\\s*(?:todo|TODO)(?:\\s+(.*?))?\\s*$";
    /**
     * 内容
     */
    private String content;

    public @Nullable TodoMarkSyntax init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        content = matcher.group(1);
        return this;
    }

    public static @Nullable SelectAndReplaceAO parsing(String lineContent) {
        SelectAndReplaceAO vo = parsingRemove(lineContent);
        if (vo != null) {
            return vo;
        }
        return parsingInsert(lineContent);
    }

    private static @Nullable SelectAndReplaceAO parsingInsert(String lineContent) {
        String regex = "//\\s*(?:todo|TODO)(\\s+.*)?";
        if (StringUtils.isNotBlank(lineContent) && Pattern.compile(regex).matcher(lineContent).find()) {
            return null;
        }
        SelectAndReplaceAO vo = new SelectAndReplaceAO();
        int length = lineContent.endsWith("\n") ? lineContent.length() - 1 : lineContent.length();
        vo.setStartOffset(length);
        vo.setEndOffset(vo.getStartOffset());
        String content = lineContent.endsWith(" \n") || lineContent.endsWith(" ") ? "// todo" : " // todo";
        vo.setReplaceContent(content);
        return vo;
    }

    private static @Nullable SelectAndReplaceAO parsingRemove(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        String regex = "(\\s*//)\\s*(?:todo|TODO)(?:\\s+.*?)?\\s*$";
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        SelectAndReplaceAO vo = new SelectAndReplaceAO();
        vo.setStartOffset(matcher.start(1));
        int length = lineContent.endsWith("\n") ? lineContent.length() - 1 : lineContent.length();
        vo.setEndOffset(length);
        vo.setReplaceContent("");
        return vo;
    }
}
