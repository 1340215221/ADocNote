package com.rh.note.util;

import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleScrollPane;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.swing.JScrollBar;

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
        if (lineNumber == null || adocTextPane == null || titleScrollPane == null) {
            return;
        }

        double rowCount = adocTextPane.getDocument().getLength();

        JScrollBar bar = titleScrollPane.getVerticalScrollBar();
        double maximum = bar.getMaximum();
        double height = bar.getHeight();

        int value = (int) (Double.valueOf(lineNumber) * maximum / rowCount - height * 4 / 10);
        System.out.println(String.format("lineNumber=[%s], max=[%s], height=[%s], value=[%s]", lineNumber, maximum, height, value));
        bar.setValue(value);
    }

}
