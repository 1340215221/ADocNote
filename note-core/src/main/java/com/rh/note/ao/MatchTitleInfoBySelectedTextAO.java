package com.rh.note.ao;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建include指向文件 参数
 */
@Data
@Accessors(chain = true)
public class MatchTitleInfoBySelectedTextAO {
    /**
     * 生成include文本
     */
    private String titleText;
}
