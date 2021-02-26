package com.rh.note.ao;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标记未完成 参数
 */
public class MarkTodoAO {
    /**
     * 判断todo存在正则
     */
    private static final String insert_regex = "//\\s*(?:todo|TODO)(\\s+.*)?";
    /**
     * 查找todo位置正则
     */
    private static final String remove_regex = "(\\s*//)\\s*(?:todo|TODO)(?:\\s+.*?)?\\s*$";

    public SelectAndReplaceAO parsing(String lineContent) {
        SelectAndReplaceAO vo = parsingRemove(lineContent);
        if (vo != null) {
            return vo;
        }
        return parsingInsert(lineContent);
    }

    private @Nullable SelectAndReplaceAO parsingInsert(String lineContent) {
        if (StringUtils.isNotBlank(lineContent) && Pattern.compile(insert_regex).matcher(lineContent).find()) {
            return null;
        }
        SelectAndReplaceAO vo = new SelectAndReplaceAO();
        int length = lineContent.endsWith("\n") ? lineContent.length() - 1 : lineContent.length();
        vo.setStartOffset(length);
        vo.setEndOffset(vo.getStartOffset());
        String content = StringUtils.isBlank(lineContent) || lineContent.endsWith(" \n") || lineContent.endsWith(" ") ? "// todo" : " // todo";
        vo.setReplaceContent(content);
        return vo;
    }

    private @Nullable SelectAndReplaceAO parsingRemove(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(remove_regex).matcher(lineContent);
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
