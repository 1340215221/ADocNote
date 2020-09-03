package com.rh.note


import javax.swing.*
import java.awt.FlowLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class TestApplication {
    public static void main(String[] args) {
        JButton but1=new JButton("模态对话框");
        JButton but2=new JButton("非模态对话框");
        JFrame f=new JFrame("DialogDemo");
        f.setSize(500, 400);
        f.setLocation(300, 200);
        f.setLayout(new FlowLayout());//设置布局管理器
        f.add(but1);
        f.add(but2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置默认关闭按钮
        f.setVisible(true);

        final JLabel label=new JLabel();//创建一个标签
        final JDialog dialog=new JDialog(f,"Dialog");
        dialog.setSize(220,150);
        dialog.setLocation(400, 300);
        dialog.setLayout(new FlowLayout());
        final JButton but3=new JButton("确认");
        dialog.add(but3);

        //为模态对话框添加点击事件
        but1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dialog.setModal(true);//设置对话框为模态
                if(dialog.getComponents().length==1){//若对话框中还没有添加标签则添加上
                    dialog.add(label);
                }
                label.setText("模式对话窗，点击确认关闭");//修改标签内容
                dialog.setVisible(true);
            }
        });

        but2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dialog.setModal(false);//设置对话框为模态
                if(dialog.getComponents().length==1){//若对话框中还没有添加标签则添加上
                    dialog.add(label);
                }
                label.setText("模式对话窗，点击确认关闭");//修改标签内容
                dialog.setVisible(true);
            }
        });

        but3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dialog.dispose();
            }
        });
    }
}
