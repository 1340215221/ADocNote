package com.rh.note.load

import com.rh.note.util.ISwingBuilder
import com.rh.note.view.ProjectListFrame;

/**
 * 加载项目列表的参数
 */
class ProjectListLoader implements ISwingBuilder {
    /**
     * 关闭窗口
     */
    void closeFrame() {
        swingBuilder."${ProjectListFrame.id}".visible = false
    }
}
