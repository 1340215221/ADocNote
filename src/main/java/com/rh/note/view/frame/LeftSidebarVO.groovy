package com.rh.note.view.frame

import com.rh.note.utils.Builder
import com.rh.note.utils.IInit

/**
 * 左侧边栏
 * 文件列表标签, 用于控制文件列表显隐
 *
 * 宽固定
 */
class LeftSidebarVO implements IInit, Builder {

    @Override
    void init() {
        def layout = {
            // 一行一列
            swingBuilder.tableLayout() {
                // 文件标签
            }
        }

        swingBuilder.panel(){
            layout()
        }
    }

    @Override
    String getId() {
        return 'leftSidebar'
    }
}
