package com.rh.note.ao;

import org.apache.commons.lang3.StringUtils;

/**
 * 输入提示项 参数
 */
public class InputPromptItemAO {

    /**
     * 完整值
     */
    private String completeValue;
    /**
     * 显示说明
     */
    private String description;

    public String getCompleteValue() {
        return completeValue;
    }

    public InputPromptItemAO setCompleteValue(String completeValue) {
        this.completeValue = completeValue;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public InputPromptItemAO setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 判断是否匹配
     */
    public boolean match(String incompleteContent) {
        return StringUtils.isNotBlank(completeValue) && (StringUtils.isBlank(incompleteContent)
                || completeValue.toLowerCase().startsWith(incompleteContent.toLowerCase())); // 首字母匹配,不区分大小写
    }
}
