package com.rh.note.view;

import com.rh.note.builder.ProgressDialogBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 进度弹窗 视图
 */
public class ProgressDialogView extends BaseView<ProgressDialogBuilder, JDialog> {

    public @NotNull ProgressDialogView init() {
        return super.init();
    }

    protected @NotNull JDialog dialog() {
        return super.getComponent(ProgressDialogBuilder::getDialog);
    }

    public void show() {
        dialog().setVisible(true);
    }

    /**
     * 关闭弹窗
     */
    public void close() {
        dialog().setVisible(false);
    }
}
