package com.rh.note.view;

import com.rh.note.ao.IncludePromptAO;
import com.rh.note.base.Init;
import com.rh.note.builder.InputPromptListBuilder;
import com.rh.note.component.InputPromptMenuItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 输入提示菜单
 */
public class InputPromptMenuView extends Init<JPopupMenu> {

    public @NotNull InputPromptMenuView init() {
        return super.init(InputPromptListBuilder.id());
    }

    private @NotNull JPopupMenu popupMenu() {
        return getBean();
    }

    /**
     * 批量添加
     */
    public void addAll(List<InputPromptItemView> items) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        // 清除旧数据
        clearItems();
        // 批量添加
        items.stream()
                .map(InputPromptItemView::getBean)
                .forEach(popupMenu()::add);
    }

    /**
     * 展示
     */
    public void show(IncludePromptAO ao) {
        if (ao == null || ao.getTextPane() == null) {
            return;
        }
        if (popupMenu().isVisible()) {
            popupMenu().setVisible(false);
            popupMenu().setVisible(true);
            return;
        }
        popupMenu().show(ao.getTextPane(), ao.getX(), ao.getY());
    }

    /**
     * todo 选择中操作被随后一个event给覆盖了
     * 默认选中第一个
     */
    public void selectFirst() {
        Component[] arr = popupMenu().getComponents();
        if (ArrayUtils.isEmpty(arr)) {
            return;
        }
        popupMenu().setSelected(arr[0]);
    }

    /**
     * 隐藏
     */
    public void hidden() {
        popupMenu().setVisible(false);
    }

    /**
     * 清除选项
     */
    public void clearItems() {
        int count = popupMenu().getComponentCount();
        if (count < 1) {
            return;
        }
        popupMenu().removeAll();
    }

    /**
     * 获得所有完整值
     */
    public List<String> getAllValues() {
        Component[] components = popupMenu().getComponents();
        if (ArrayUtils.isEmpty(components)) {
            return Collections.emptyList();
        }
        return Arrays.stream(components)
                .filter(c -> c instanceof InputPromptMenuItem)
                .map(c -> ((InputPromptMenuItem) c))
                .map(InputPromptMenuItem::getResult)
                .collect(Collectors.toList());
    }
}
