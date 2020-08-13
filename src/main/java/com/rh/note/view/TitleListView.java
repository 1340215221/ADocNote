package com.rh.note.view;

import com.rh.note.builder.TitleListBuilder;
import com.rh.note.model.component.Init;

import javax.swing.*;

/**
 * 标题列表
 */
public class TitleListView extends Init<JScrollPane> {

    public TitleListView init() {
        return super.init(TitleListBuilder.getId());
    }

    /**
     * 隐藏或显示
     */
    public void hiddenOrShow() {
        titleList().setVisible(!titleList().isVisible());
    }

    private JScrollPane titleList() {
        return getBean();
    }
}
