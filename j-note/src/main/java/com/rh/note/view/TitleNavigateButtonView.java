package com.rh.note.view;

import com.rh.note.builder.TitleNavigateButtonBuilder;
import com.rh.note.component.TitleNavigateButton;
import com.rh.note.line.TitleLine;
import com.rh.note.util.Init;

/**
 * {@link TitleNavigateButtonBuilder#id}
 */
public class TitleNavigateButtonView extends Init<TitleNavigateButton> {

    public static void create(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }

        new TitleNavigateButtonBuilder(titleLine).init();
    }

    public TitleNavigateButtonView initByTitleName(String titleName) {
        return super.init(TitleNavigateButtonBuilder.id(titleName));
    }

    private TitleNavigateButton navigateButton() {
        return getBean();
    }
}
