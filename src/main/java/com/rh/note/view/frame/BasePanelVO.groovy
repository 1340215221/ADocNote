package com.rh.note.view.frame


import com.rh.note.utils.Builder

import static com.rh.note.constant.SwingKeyEnum.BOTTOM_SIDEBAR
import static com.rh.note.constant.SwingKeyEnum.CENTER_AREA
import static com.rh.note.constant.SwingKeyEnum.HEAD_MENU
import static com.rh.note.constant.SwingKeyEnum.LEFT_SIDEBAR

/**
 * 基础面板
 */
class BasePanelVO implements Builder {

    /**
     * 左侧边栏
     */
    private LeftSidebarVO leftSidebar = new LeftSidebarVO()
    /**
     * 底边栏
     */
    private BottomSidebarVO bottomSidebar = new BottomSidebarVO()
    /**
     * 头部菜单
     */
    private HeadMenuVO headMenu = new HeadMenuVO()
    /**
     * 中间区域
     */
    private CenterAreaVO centerArea = new CenterAreaVO()

    void init() {
        centerArea.init()
        headMenu.init()
        bottomSidebar.init()
        leftSidebar.init()

        def mainLayout = {
            swingBuilder.borderLayout(center: CENTER_AREA.get(),
                    north: HEAD_MENU.get(),
                    south: BOTTOM_SIDEBAR.get(),
                    west: LEFT_SIDEBAR.get(),
            )
        }

        swingBuilder.panel() {
            mainLayout()
        }
    }

    @Override
    String getId() {
        return 'basePanel'
    }
}
