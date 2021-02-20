package com.rh.note.view;

import com.rh.note.builder.ProgressDialogBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.common.IShowProgress;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 进度条 视图
 */
public class ProgressBarView extends BaseView<ProgressDialogBuilder, JProgressBar> {

    public @NotNull ProgressBarView init() {
        return super.init();
    }

    protected @NotNull JProgressBar progressBar() {
        return super.getComponent(ProgressDialogBuilder::getProgressBar);
    }

    /**
     * 获得进度回调
     */
    public @Nullable IShowProgress getCallback(ProgressLabelView progressLabelView) {
        if (progressLabelView == null) {
            return null;
        }
        JLabel label = progressLabelView.label();
        JProgressBar progressBar = progressBar();
        return new IShowProgress() {
            @Override
            public void show(String text, int progress) {
                label.setText(text);
                progressBar.setValue(progress);
            }
        };
    }
}
