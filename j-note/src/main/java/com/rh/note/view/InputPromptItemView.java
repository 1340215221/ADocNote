package com.rh.note.view;

import com.rh.note.ao.InputPromptItemAO;
import com.rh.note.base.Init;
import com.rh.note.builder.InputPromptItemBuilder;
import com.rh.note.component.InputPromptMenuItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 输入提示项
 */
public class InputPromptItemView extends Init<InputPromptMenuItem> {

    public static void create(InputPromptItemAO ao) {
        if (ao == null) {
            return;
        }
        new InputPromptItemBuilder(ao).init();
    }

    @Override
    public @Nullable InputPromptItemView init(String showValue) {
        return super.init(showValue);
    }

    private @NotNull InputPromptMenuItem menuItem() {
        return getBean();
    }

}
