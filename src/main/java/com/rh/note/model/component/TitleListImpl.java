package com.rh.note.model.component;

import com.rh.note.view.TitleList;

import javax.swing.*;

/**
 * 标题列表
 */
public class TitleListImpl extends Init<JScrollPane> {

    public TitleListImpl init() {
        return super.init(TitleList.getId());
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
