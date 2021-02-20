package com.rh.note.view;

import com.rh.note.builder.ProgressDialogBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 进度描述 视图
 */
public class ProgressLabelView extends BaseView<ProgressDialogBuilder, JLabel> {

    public @NotNull ProgressLabelView init() {
        return super.init();
    }

    protected @NotNull JLabel label() {
        return super.getComponent(ProgressDialogBuilder::getLabel);
    }
}
