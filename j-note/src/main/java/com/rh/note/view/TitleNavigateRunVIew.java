package com.rh.note.view;

import com.rh.note.builder.TitleNavigateButtonBuilder;
import lombok.Data;

import javax.swing.*;

/**
 * 标题导航栏
 */
@Data
public class TitleNavigateRunVIew extends Init<JPanel> {

    public TitleNavigateRunVIew init() {
        return super.init(TitleNavigateButtonBuilder.getId());
    }
}
