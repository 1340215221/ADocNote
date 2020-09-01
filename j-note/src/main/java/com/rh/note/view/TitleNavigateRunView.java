package com.rh.note.view;

import com.rh.note.builder.EditAreaBuilder;
import lombok.Data;

import javax.swing.*;

/**
 * 标题导航栏
 */
@Data
public class TitleNavigateRunView extends Init<JPanel> {

    public TitleNavigateRunView init() {
        return super.init(EditAreaBuilder.getNavigateId());
    }

    protected JPanel panel() {
        return getBean();
    }

    /**
     * 添加标题
     */
    public void add(TitleNavigateButtonRunView titleNavigateButton) {
        panel().add(titleNavigateButton.getBean());
    }

    /**
     * 清空导航
     */
    public void clearTitle() {
        panel().removeAll();
        panel().updateUI();
        panel().repaint();
    }
}
