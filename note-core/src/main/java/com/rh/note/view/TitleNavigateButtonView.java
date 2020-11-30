package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TitleNavigateButtonBuilder;
import com.rh.note.component.TitleButton;
import com.rh.note.path.TitleBeanPath;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 标题导航栏按钮
 */
public class TitleNavigateButtonView extends Init<TitleButton> {

    public static void create(TitleBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }

        new TitleNavigateButtonBuilder(beanPath).init();
    }

    /**
     * 转换
     */
    public static @Nullable TitleNavigateButtonView cast(TitleButton source) {
        if (source == null) {
            return null;
        }
        String beanPath = source.getBeanPath().getBeanPath();
        if (StringUtils.isBlank(beanPath)) {
            return null;
        }
        return new TitleNavigateButtonView().initByBeanPath(beanPath);
    }

    public @Nullable TitleNavigateButtonView initByBeanPath(String beanPath) {
        return super.init(TitleNavigateButtonBuilder.id(beanPath));
    }

    private @NotNull TitleButton navigateButton() {
        return getBean();
    }

    /**
     * 获得文件路径
     */
    public @NotNull String getFilePath() {
        TitleBeanPath beanPath = (TitleBeanPath) navigateButton().getBeanPath();
        return beanPath.getFilePath();
    }

    /**
     * 获得对象路径
     */
    public @NotNull TitleBeanPath getBeanPath() {
        return (TitleBeanPath) navigateButton().getBeanPath();
    }
}
