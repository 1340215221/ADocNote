package com.rh.note.line;

import com.rh.note.common.IArgsConstructorBean;
import com.rh.note.syntax.TitleSyntax;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标题行
 */
@Data
public class TitleLine implements IArgsConstructorBean {
    /**
     * 子标题
     */
    private final List<TitleLine> childrenTitles = new ArrayList<>();
    /**
     * 语法对象
     */
    private TitleSyntax titleSyntax;

    public @Nullable TitleLine init(String lineText) {
        if (StringUtils.isBlank(lineText)) {
            return null;
        }
        Matcher matcher = Pattern.compile("").matcher(lineText);
        if (!matcher.find()) {
            return null;
        }
    }

    /**
     * 获得标题名
     */
    public String getTitleName() {
        return titleSyntax.getTitleName();
    }

    /**
     * 获得标题路径字符串
     */
    public String getBeanPathStr() {
        // todo
        return "temp getBeanPathStr";
    }

    @Override
    public @NotNull String[] getBeanNameArgs() {
        return new String[0];
    }
}
