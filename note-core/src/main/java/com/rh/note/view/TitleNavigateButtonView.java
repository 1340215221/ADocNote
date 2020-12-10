package com.rh.note.view;

import com.rh.note.builder.TitleNavigateButtonBuilder;
import com.rh.note.common.IPrototypeView;
import com.rh.note.component.TitleButton;
import com.rh.note.path.TitleBeanPath;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 标题导航栏按钮
 */
public class TitleNavigateButtonView extends IPrototypeView<TitleNavigateButtonBuilder, TitleButton> {

    public @NotNull TitleNavigateButtonView create(TitleBeanPath beanPath) {
        return super.create(beanPath);
    }

    public @Nullable TitleNavigateButtonView init(String beanPath) {
        return super.init(beanPath);
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
        return new TitleNavigateButtonView().init(beanPath);
    }

    protected @NotNull TitleButton navigateButton() {
        return super.getComponent(TitleNavigateButtonBuilder::getNavigateButton);
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
