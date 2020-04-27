package com.rh.note.view.frame

import com.rh.note.utils.Builder
import com.rh.note.utils.IInit

/**
 * 顶部菜单
 * - 主菜单
 * - 导航栏(文件路径)
 * - git 提交,更新,推送,历史,还原
 *
 * 高固定
 */
class HeadMenuVO implements Builder {

    @Override
    void init() {
        def layout = {
            swingBuilder.tableLayout(){
                // 主菜单
                flowLayout(){
                }

                // 导航栏(文件路径)
                flowLayout(){
                }
            }
        }

        swingBuilder.panel(){
            layout()
        }
    }

    @Override
    String getId() {
        return 'headMenu'
    }
}
