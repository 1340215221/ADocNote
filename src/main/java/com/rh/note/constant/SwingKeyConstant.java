package com.rh.note.constant;

import com.rh.note.view.frame.BottomSidebarVO;
import com.rh.note.view.frame.CenterAreaVO;
import com.rh.note.view.frame.HeadMenuVO;
import com.rh.note.view.frame.LeftSidebarVO;

import java.util.function.Consumer;

public interface SwingKeyConstant {
    /**
     * 头部菜单
     */
    Consumer<HeadMenuVO> HEAD_MENU_CSM = HeadMenuVO::init;
    /**
     * 中间区域
     */
    Consumer<CenterAreaVO> CENTER_AREA_CSM = CenterAreaVO::init;
    /**
     * 底边栏
     */
    Consumer<BottomSidebarVO> BOTTOM_SIDEBAR_CSM = BottomSidebarVO::init;
    /**
     * 底边栏
     */
    Consumer<LeftSidebarVO> LEFT_SIDEBAR_CSM = LeftSidebarVO::init;
}
