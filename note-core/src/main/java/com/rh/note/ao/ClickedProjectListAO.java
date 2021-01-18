package com.rh.note.ao;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 点击项目列表 参数
 */
@Data
@Accessors(chain = true)
public class ClickedProjectListAO {
    /**
     * 项目路径
     */
    private String proPath;
}
