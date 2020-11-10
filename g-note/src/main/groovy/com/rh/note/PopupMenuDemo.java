package com.rh.note;

import org.apache.commons.io.FileUtils;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.MenuSelectionManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PopupMenuDemo {
    PopupMenuDemo() throws Exception {
        final JFrame f = new JFrame("演示");
        final JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setSize(400, 100);
        final JPopupMenu popupmenu = new JPopupMenu("编辑");
        JMenuItem cut = new JMenuItem("剪切");
        JMenuItem copy = new JMenuItem("复制");
        JMenuItem paste = new JMenuItem("粘贴");
        File file = new File("icon/file/content.png");
        byte[] bytes = null;
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(bytes);
        label.setIcon(icon);
        label.validate();
        label.repaint();
        popupmenu.add(cut);
        popupmenu.add(copy);
        popupmenu.add(paste);
        f.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //获取鼠标点击的位置
                popupmenu.show(f, e.getX(), e.getY());
                cut.setSelected(true);
                cut.setForeground(Color.CYAN);
            }
        });
        popupmenu.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("keyTyped");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("keyPressed");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("keyReleased");
            }
        });
        KeyEvent event = new KeyEvent(popupmenu, 402, System.currentTimeMillis(), 0, 40);
        popupmenu.dispatchEvent(event);
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("剪切菜单项选中");
            }
        });
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("复制菜单项选中");
            }
        });
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("粘贴菜单项选中");
            }
        });
        f.add(label);
        f.add(popupmenu);
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) throws Exception {
        new PopupMenuDemo();
    }
}
