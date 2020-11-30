package com.rh.note.view;

import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.UnknownLogicException;
import lombok.NonNull;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

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
        ImageIcon icon = new ImageIcon("icon/image/save_ok.gif");

        JDialog dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.getContentPane().setBackground(Color.PINK);
        dialog.getContentPane().setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        JLabel label = new JLabel(icon);
//        JLabel label = new JLabel(message.getValue());
//        label.setForeground(Color.black);

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
