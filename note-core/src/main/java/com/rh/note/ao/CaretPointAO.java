package com.rh.note.ao;

import com.rh.note.component.AdocTextPane;
import com.rh.note.view.TextPaneView;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.awt.Point;

/**
 * 光标坐标 参数
 */
@Data
@Accessors(chain = true)
public class CaretPointAO implements IncludePromptAO.ICaretPoint {
    /**
     * 编辑区控件
     */
    private AdocTextPane textPane;
    /**
     * x坐标
     */
    private int caretX = 0;
    /**
     * y坐标
     */
    private int caretY = 0;

    @Override
    public void copy(TextPaneView view) {
        if (view == null) {
            return;
        }

        textPane = view.textPane();
        Point point = view.getCaretPoint();
        if (point == null) {
            return;
        }
        caretX = (int) point.getX();
        caretY = (int) point.getY();
    }
}
