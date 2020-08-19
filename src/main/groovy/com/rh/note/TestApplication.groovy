package com.rh.note


import javax.swing.*
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.*

class TestApplication {

    JFrame mainWin = new JFrame("测试JTextPane");
    JTextPane txt = new JTextPane();
    StyledDocument doc = txt.getStyledDocument();//------model---把属性集、文本内容结合起来
//定义3个SimpleAttributeSet对象
    SimpleAttributeSet a = new SimpleAttributeSet();//---属性集---把要加的属性先放一起---再加入文本内容
    SimpleAttributeSet j = new SimpleAttributeSet();
    SimpleAttributeSet je = new SimpleAttributeSet();

    public void init() {
//为 a属性集 设置颜色、字体大小、字体和下划线
        StyleConstants.setForeground(a, Color.RED);
        StyleConstants.setFontSize(a, 24);
        StyleConstants.setFontFamily(a, "Dialog");
        StyleConstants.setUnderline(a, true);
//为 j属性集 设置颜色、字体大小、字体和粗体字
        StyleConstants.setForeground(j, Color.BLUE);
        StyleConstants.setFontSize(j, 30);
        StyleConstants.setFontFamily(j, "Arial Black");
        StyleConstants.setBold(j, true);
//为 je属性集 设置颜色、字体大小、斜体字。自己加个删除线
        StyleConstants.setForeground(je, Color.GREEN);
        StyleConstants.setFontSize(je, 30);
        StyleConstants.setItalic(je, true);
        StyleConstants.setStrikeThrough(je, true);


//设置不允许编辑
        txt.setEditable(false);
        txt.setText("先走出去看看再说\n" + "一切都会好起来的\n" + "我为什么要听它的\n");
//由model来改变组件。分别为文档中3段文字设置不同的外观样式
        doc.setCharacterAttributes(0, 12, a, true);
        doc.setCharacterAttributes(12, 12, j, true);
        doc.setCharacterAttributes(20, 30, je, true);
        mainWin.add(new JScrollPane(txt), BorderLayout.CENTER);
//获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int inset = 100;
//设置主窗口的大小
        mainWin.setBounds(inset, inset, (int) screenSize.width - inset * 2, (int) screenSize.height - inset * 2);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setVisible(true);
    }

    public static void main(String[] args) {
        new TestApplication().init();
    }

    static JTextPane build() {
        StyledDocument styledDoc = new DefaultStyledDocument()//文档模型
        JTextPane textPane = new JTextPane(styledDoc)//创建具有指定文档模型的TextPane
        //或者JTextPane textPane = new JTextPane()
        //styledDoc = textPane.getStyledDocument() // 获得textPane的Document

        textPane.setEditable(false)//设置不可编辑
        JScrollPane scrollPane = new JScrollPane(textPane)//可以给文字面板textPane添加滚条

        // 文档模型插入文字
//        styledDoc.insertString(int styledDoc.getLength(), String content, AttributeSet attrSet)
        //content为你要插入的内容
        //AttributeSet为属性集,包括字体样式（粗体斜体）、字号、前景颜色、背景颜色等
        SimpleAttributeSet attrSet = new SimpleAttributeSet()
        int size = 17
        Color color = Color.red
        Color backColor = Color.green
        String fontName = ""
        //属性集设置
        StyleConstants.setFontSize(attrSet, size) // 大小
        StyleConstants.setBold(attrSet, true) // 粗体
        StyleConstants.setItalic(attrSet, true) // 斜体
        StyleConstants.setUnderline(attrSet, true) // 下划线
        StyleConstants.setForeground(attrSet, color) // 前景颜色
        StyleConstants.setBackground(attrSet, backColor)//背景颜色
        //Color color Color backColor
        StyleConstants.setFontFamily(attrSet, fontName) //设置字体
        //String fontName 如“宋体”
        textPane.setText(null)//清空面板

        //插入文字用下述结构
        String content = ""
        try {
            styledDoc.insertString(styledDoc.getLength(), content, attrSet)
        } catch (BadLocationException e) {
            e.printStackTrace()
        }

        return textPane
    }

}
