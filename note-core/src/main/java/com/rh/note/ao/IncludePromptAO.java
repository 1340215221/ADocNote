package com.rh.note.ao;

import com.rh.note.base.Init;
import com.rh.note.component.AdocTextPane;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * include提示 参数
 */
public class IncludePromptAO {
    /**
     * 光标坐标
     */
    private ICaretPoint caretPoint;
    /**
     * 提示项
     */
    private List<InputPromptItemAO> aoList;

    public IncludePromptAO setCaretPoint(ICaretPoint caretPoint) {
        this.caretPoint = caretPoint;
        return this;
    }

    public AdocTextPane getTextPane() {
        return caretPoint != null ? caretPoint.getTextPane() : null;
    }

    public int getX() {
        return caretPoint != null ? caretPoint.getCaretX() - 10 : 0;
    }

    public int getY() {
        return caretPoint != null ? caretPoint.getCaretY() + 25 : 0;
    }

    public IncludePromptAO setAoList(List<InputPromptItemAO> aoList) {
        this.aoList = aoList;
        return this;
    }

    public List<InputPromptItemAO> getAoList() {
        return aoList;
    }

    public IncludePromptAO copy(String[] fileNameArr, String incompleteContent) {
        if (ArrayUtils.isNotEmpty(fileNameArr)) {
            aoList = Arrays.stream(fileNameArr)
                    .map(e -> new InputPromptItemAO().setCompleteValue(e))
                    .filter(ao -> ao.match(incompleteContent))
                    .collect(Collectors.toList());
        }
        return this;
    }

    /**
     * 光标坐标接口
     */
    public static interface ICaretPoint<T extends Init> {
        AdocTextPane getTextPane();
        int getCaretX();
        int getCaretY();
        void copy(T view);
    }
}
