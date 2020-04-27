package com.rh.note.view.frame

import com.rh.note.utils.Builder
import com.rh.note.utils.IInit

/**
 * 底边栏
 * - git分支
 * - 选中 字符数,行数
 * - 光标所在 行,列
 *
 * 高固定
 */
class BottomSidebarVO implements Builder {

    void init() {
    }

    @Override
    String getId() {
        return 'bottomSidebar'
    }
}
