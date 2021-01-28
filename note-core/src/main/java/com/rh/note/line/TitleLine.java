package com.rh.note.line;

import com.rh.note.common.IArgsConstructorBean;
import com.rh.note.common.ReadTitleLineImpl;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.view.RootTitleNodeView;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 标题行
 */
@Getter
@Setter
@Accessors(chain = true)
public class TitleLine implements IArgsConstructorBean, ReadTitleLineImpl {
    /**
     * 父标题
     */
    private TitleLine parentTitle;
    /**
     * 子标题
     */
    private final List<TitleLine> childrenTitle = new ArrayList<>();
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
    public @Nullable String getBeanPathStr() {
        return beanPath != null ? beanPath.toString() : null;
    }

    @Override
    public @NotNull String[] getBeanNameArgs() {
        return RootTitleNodeView.beanArg;
    }

    public void addChildrenTitles(TitleLine childrenTitle) {
        if (childrenTitle == null) {
            return;
        }
        this.childrenTitle.add(childrenTitle);
    }

    /**
     * 获得行号
     */
    public @Nullable Integer getLineNumber() {
        return Optional.ofNullable(beanPath)
                .map(TitleBeanPath::getLineNumber)
                .orElse(null);
    }

    @Override
    public @Nullable Integer getLevel() {
        return Optional.ofNullable(titleSyntax)
                .map(TitleSyntax::getLevel)
                .orElse(null);
    }

    @Override
    public @NotNull List<TitleLine> getChildrenTitle() {
        return childrenTitle;
    }

    @Override
    public void setParentTitle(ReadTitleLineImpl titleLine) {
        if (titleLine instanceof TitleLine) {
            parentTitle = (TitleLine) titleLine;
        }
    }

    public @Nullable TitleBeanPath getBeanPath() {
        if (beanPath == null) {
            return null;
        }
        if (StringUtils.isBlank(beanPath.getTitlePath())) {
            beanPath.setTitlePath(this);
        }
        return beanPath;
    }
}