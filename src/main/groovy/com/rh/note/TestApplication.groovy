package com.rh.note

import javax.swing.*
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.*

class TestApplication {

    static main(args) {
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
    }

}
