package com.rh.note.view;

import com.rh.note.builder.TitleNavigateButtonBuilder;
import com.rh.note.grammar.TitleGrammar;

import javax.swing.*;

/**
 * 标题导航栏按钮
 */
public class TitleNavigateButtonRunView extends Init<JButton> {

    public static void create(TitleGrammar title) {
        new TitleNavigateButtonBuilder(title);
    }

    @Override
    public TitleNavigateButtonRunView init(String componentId) {
        return super.init(componentId);
    }
}
