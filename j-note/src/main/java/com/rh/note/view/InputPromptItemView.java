package com.rh.note.view;

import com.rh.note.ao.InputPromptItemAO;
import com.rh.note.base.Init;
import com.rh.note.builder.InputPromptItemBuilder;
import com.rh.note.component.InputPromptMenuItem;
import com.rh.note.util.ViewUtil;
import org.apache.commons.lang3.StringUtils;
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

    public static void deleteByValue(String value) {
        if (StringUtils.isBlank(value)) {
            return;
        }
        ViewUtil.removeByComponentId(InputPromptItemBuilder.id(value));
    }

    public static @Nullable InputPromptItemView cast(InputPromptMenuItem bean) {
        if (bean == null) {
            return null;
        }
        return new InputPromptItemView().init(bean.getResult());
    }

    @Override
    public @Nullable InputPromptItemView init(String showValue) {
        return super.init(InputPromptItemBuilder.id(showValue));
    }

    private @NotNull InputPromptMenuItem menuItem() {
        return getBean();
    }

    /**
     * 获得实际值
     */
    public @NotNull String getValue() {
        return menuItem().getResult();
    }
}
