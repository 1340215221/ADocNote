package com.rh.note.util;

import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleScrollPane;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.swing.JScrollBar;
import javax.swing.text.Caret;
import javax.swing.text.Element;

/**
 * 编辑区滚动定位工具
 */
@Builder
@AllArgsConstructor
public class ScrollPositionUtil {

    /**
     * 定位行号
     */
    private Integer lineNumber;
    /**
     * 编辑区控件
     */
    private AdocTextPane adocTextPane;
    /**
     * 滚动控件
     */
    private TitleScrollPane titleScrollPane;
    /**
     * 定位到指定行
     */
    public void positioningToTitleRow() {
        this.moveCursorToTitleRow();
        this.scrollToTitleRow();
    }

    /**
     * 光标移动到指定行
     */
    private void moveCursorToTitleRow() {
        if (lineNumber == null || adocTextPane == null || titleScrollPane == null) {
            return;
        }

        Element rootElement = adocTextPane.getDocument().getDefaultRootElement();
        Element element = null;
        if (rootElement.getElementCount() < lineNumber) {
            element = rootElement.getElement(rootElement.getElementCount() - 1);
        } else {
            element = rootElement.getElement(lineNumber - 1);
        }

        Caret caret = adocTextPane.getCaret();
        caret.setDot(element.getStartOffset());
        caret.setVisible(true);
    }

    /**
     * 滚动到指定行
     */
    private void scrollToTitleRow() {
        if (lineNumber == null || adocTextPane == null || titleScrollPane == null) {
            return;
        }

        double rowCount = adocTextPane.getDocument().getDefaultRootElement().getElementCount();

        JScrollBar bar = titleScrollPane.getVerticalScrollBar();
        double maximum = bar.getMaximum();
        double height = bar.getHeight();

        int value = (int) (Double.valueOf(lineNumber) * maximum / rowCount - height * 2 / 5);
        bar.setValue(value);
    }

}
