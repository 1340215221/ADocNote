package com.rh.note.path;

import com.rh.note.common.BaseFileConfig;
import com.rh.note.line.TitleLine;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 标题对象路径
 */
@Getter
@Setter
@Accessors(chain = true)
public class TitleBeanPath extends BaseFileConfig {
    /**
     * 文件相对路径
     */
    private String filePath;
    /**
     * 文件内标题路径
     */
    @Setter(AccessLevel.NONE)
    private String titlePath;
    /**
     * 行号
     */
    private int lineNumber;

    /**
     * 在TitleLine第一次调用getBeanPath时, 只生成一次titlePath
     */
    public void setTitlePath(TitleLine titleLine) {
        if (titleLine == null || StringUtils.isBlank(titleLine.getTitleName())) {
            return;
        }
        titlePath = getParentName(titleLine);
    }

    /**
     * 递归拼接所有父标题名和当前标题名
     */
    private @Nullable String getParentName(TitleLine titleLine) {
        if (titleLine == null || StringUtils.isBlank(titleLine.getTitleName())) {
            return null;
        }
        TitleLine parentTitle = titleLine.getParentTitle();
        String parentName = getParentName(parentTitle);
        return StringUtils.isNotBlank(parentName) ? parentName + "$" + titleLine.getTitleName() : titleLine.getTitleName();
    }

    @Override
    public @Nullable String toString() {
        if (StringUtils.isBlank(filePath) && StringUtils.isBlank(titlePath)) {
            return null;
        }
        String result = "";
        if (StringUtils.isNotBlank(filePath)) {
            result += filePath;
        }
        if (StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(titlePath)) {
            result += "=>";
        }
        if (StringUtils.isNotBlank(titlePath)) {
            result += titlePath;
        }
        return result;
    }

    /**
     * 获得绝对路径
     * todo 有没有办法控制下这个类的生成呢, 部分属性必定不为空
     */
    public @NotNull String getAbsolutePath() {
        return getProPath() + filePath;
    }
}
