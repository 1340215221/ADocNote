package com.rh.note.line;

import com.rh.note.common.IArgsConstructorBean;
import com.rh.note.common.IReadTitleLine;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.TitleSyntax;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题行
 */
@Data
public class TitleLine implements IArgsConstructorBean, IReadTitleLine {
    /**
     * 父标题
     */
    private TitleLine parentTitle;
    /**
     * 子标题
     */
    private final List<TitleLine> childrenTitles = new ArrayList<>();
    /**
     * 语法对象
     */
    private TitleSyntax titleSyntax;
    /**
     * 对象路径
     */
    private TitleBeanPath beanPath;

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

    public void addChildrenTitles(TitleLine childrenTitle) {
        if (childrenTitle == null) {
            return;
        }
        childrenTitles.add(childrenTitle);
    }
}
