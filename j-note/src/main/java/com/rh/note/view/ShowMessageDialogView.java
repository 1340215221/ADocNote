package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.UnknownLogicException;
import lombok.NonNull;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * 展示消息弹窗
 */
public class ShowMessageDialogView {

    /**
     * 展示内容
     */
    private PromptMessageEnum message;

    public ShowMessageDialogView init(@NonNull PromptMessageEnum message) {
        this.message = message;
        return this;
    }

    /**
     * todo
     * 展示
     * 可多次执行
     */
    public void showAndClose() {
        JDialog dialog = new JDialog();
        dialog.setLocationRelativeTo(null);
        dialog.setSize(80, 50);
        dialog.setUndecorated(true);
        dialog.getContentPane().setBackground(Color.PINK);

        JLabel label = new JLabel(message.getValue());
        label.setForeground(Color.black);

        dialog.add(label);
        dialog.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new UnknownLogicException();
        }
        dialog.dispose();
    }

}
