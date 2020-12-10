package com.rh.note.view;

import com.rh.note.ao.IncludePromptAO;
import com.rh.note.builder.InputPromptListBuilder;
import com.rh.note.common.ISingletonView;
import com.rh.note.component.InputPromptMenuItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.SingleSelectionModel;
import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 输入提示菜单
 */
public class InputPromptMenuView extends ISingletonView<InputPromptListBuilder, JPopupMenu> {

    public @NotNull InputPromptMenuView init() {
        return super.init();
    }

    private @NotNull JPopupMenu popupMenu() {
        return super.getComponent(InputPromptListBuilder::getPopupMenu);
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
                .map(InputPromptItemView::menuItem)
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
        SingleSelectionModel model = popupMenu().getSelectionModel();
        model.setSelectedIndex(0);
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

    public boolean isVisible() {
        return popupMenu().isVisible();
    }

    /**
     * 下键
     */
    public void next() {
        int count = popupMenu().getComponents().length;
        if (count < 2) {
            return;
        }
        SingleSelectionModel model = popupMenu().getSelectionModel();
        int index = model.getSelectedIndex();
        // 如果没有选择, 下键时, 选择第二个
        if (index < 0) {
            model.setSelectedIndex(1);
            return;
        }
        // 如果超出最后一个, 选择第一个
        int selectedIndex = model.getSelectedIndex();
        if (selectedIndex + 1 > count - 1) {
            model.setSelectedIndex(0);
            return;
        }
        // 选择下一个
        model.setSelectedIndex(selectedIndex + 1);
    }

    /**
     * 显示选择
     */
    public void showSelected() {
        // todo
        int index = popupMenu().getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        Component component = popupMenu().getComponent(index);
        if (!(component instanceof MenuElement)) {
            return;
        }
    }

    /**
     * 上键
     */
    public void previous() {
        int count = popupMenu().getComponents().length;
        if (count < 2) {
            return;
        }
        SingleSelectionModel model = popupMenu().getSelectionModel();
        int index = model.getSelectedIndex();
        // 如果没有选择, 上键时, 选择最后一个
        if (index < 0) {
            model.setSelectedIndex(count - 1);
            return;
        }
        // 如果超出第一个, 选择最后一个
        int selectedIndex = model.getSelectedIndex();
        if (selectedIndex - 1 < 0) {
            model.setSelectedIndex(count - 1);
            return;
        }
        // 选择下一个
        model.setSelectedIndex(selectedIndex - 1);
    }
}
