package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TitleNavigateButtonBuilder;
import com.rh.note.component.TitleButton;
import com.rh.note.path.TitleBeanPath;
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

    public @Nullable TitleNavigateButtonView initByTitleName(String titleName) {
        return super.init(TitleNavigateButtonBuilder.id(titleName));
    }

    private @NotNull TitleButton navigateButton() {
        return getBean();
    }
}
