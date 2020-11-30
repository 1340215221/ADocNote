package com.rh.note.path;

import com.rh.note.base.ITitleBeanPath;
import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.exception.RequestParamsValidException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;

/**
 * 标题地址
 * todo
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TitleBeanPath implements ITitleBeanPath {

    /**
     * 文件地址
     */
    @NonNull
    @Getter
    private String filePath;
    /**
     * 文件内标题路径
     */
    @NonNull
    private String titlePath;

    public static @Nullable TitleBeanPath getInstance(String filePath, String titlePath) {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(titlePath)) {
            return null;
        }
        return new TitleBeanPath(filePath, titlePath);
    }

    @Override
    public Color getColor() {
        if (AdocFileTypeEnum.content.matchByFPath(filePath)) {
            return Color.green;
        }
        if (AdocFileTypeEnum.towLevel.matchByFPath(filePath)) {
            return Color.pink;
        }
        return Color.lightGray;
    }

    @Override
    public String getTitleName() {
        if (StringUtils.isEmpty(titlePath)) {
            throw new RequestParamsValidException();
        }
        int index = titlePath.lastIndexOf(".");
        return titlePath.substring(index + 1);
    }

    @Override
    public String getBeanPath() {
        return filePath + "#" + titlePath;
    }
}
