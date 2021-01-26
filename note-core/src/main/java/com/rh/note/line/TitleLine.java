package com.rh.note.line;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题行
 */
public class TitleLine {
    public @Nullable TitleLine init(String lineText) {
        if (StringUtils.isBlank(lineText)) {
            return null;
        }
        Matcher matcher = Pattern.compile("").matcher(lineText);
        if (!matcher.find()) {
            return null;
        }
    }
}
