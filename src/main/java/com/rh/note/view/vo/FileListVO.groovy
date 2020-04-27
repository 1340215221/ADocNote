package com.rh.note.view.vo

import com.rh.note.utils.Builder
import com.rh.note.utils.IInit

/**
 * 文件列表
 * - 文件列表
 * - 定位文件在列表中的位置
 * - 全部展开
 * - 全部收起
 *
 * 按钮固定大小
 * 设置最小大小
 */
class FileListVO implements Builder {

    @Override
    void init() {
        swingBuilder.panel(){
            // 按钮控件栏
            // 文件树
        }
    }

    @Override
    String getId() {
        return 'fileList'
    }
}
