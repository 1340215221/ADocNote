package com.rh.note.util;

import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class StrUtils {

    /**
     * 占位符
     */
    private static final String placeholder_regex = "\\{\\}";
    private static final String placeholder = "{}";

    /**
     * 替换占位符
     */
    public static String replacePlaceholder(String str, String... args) {
        if (StringUtils.isBlank(str) || ArrayUtils.isEmpty(args)) {
            return str;
        }
        String[] strArr = str.split(placeholder_regex);
        if (ArrayUtils.isEmpty(strArr)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (String arg : args) {
            int index = sb.indexOf(placeholder);
            if (index < 0) {
                throw new ApplicationException(ErrorCodeEnum.THE_PLACEHOLDER_AND_THE_NUMBER_OF_PARAMETERS_ARE_NOT_EQUAL);
            }
            sb.replace(index, index + 2, arg);
        }
        return sb.toString();
    }
}
