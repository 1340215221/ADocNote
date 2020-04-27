package com.rh.note.view.vo

import com.rh.note.utils.Builder
import com.rh.note.utils.IInit

class EditAreaVO implements IInit, Builder {

    /**
     * 编辑区域 <br>
     * 文件标签 <br>
     *     高固定
     * 文件编辑区域 <br>
     * 左侧栏(行号, 块标识)
     *    宽固定
     */
    @Override
    void init() {
    }

    @Override
    String getId() {
        return 'editArea'
    }

}
