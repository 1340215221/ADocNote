package com.rh.note.line;

import com.rh.note.syntax.TitleSyntax;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTextPane;
import javax.swing.text.Element;

/**
 * 标题行
 */
public class TitleLine {

    /**
     * 语法内容
     */
    private TitleSyntax titleSyntax;

    /**
     * 指定解析第几行的内容
     */
    public static @Nullable TitleLine parseByLineNumber(JTextPane textPane, Integer lineNumber) {
        if (textPane == null || lineNumber == null) {
            return null;
        }
        Element rootElement = textPane.getDocument().getDefaultRootElement();
        Element element = rootElement.getElement(lineNumber - 1);
        if (element == null) {
            return null;
        }
        try {
            String lineContent = textPane.getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
            TitleSyntax titleSyntax = new TitleSyntax().init(lineContent);
            if (titleSyntax == null) {
                return null;
            }
            TitleLine titleLine = new TitleLine();
            titleLine.titleSyntax = titleSyntax;
            return titleLine;
        } catch (Exception e) {
            return null;
        }
    }

}
