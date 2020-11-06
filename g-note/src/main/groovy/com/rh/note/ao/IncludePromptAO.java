package com.rh.note.ao;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * include提示 参数
 */
public class IncludePromptAO {
    /**
     * 提示项
     */
    private List<InputPromptItemAO> aoList;

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
}
