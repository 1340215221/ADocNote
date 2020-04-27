package com.rh.note.constant

import com.rh.note.utils.Builder

import java.util.function.Consumer

/**
 * 所有通过SwingBuilder创建的控件声明的id
 */
enum SwingKeyEnum implements Builder,SwingKeyConstant {

    /**
     * 头部菜单模板
     */
    HEAD_MENU('headMenu', HEAD_MENU_CSM),
    /**
     * 中间区域
     */
    CENTER_AREA('centerArea', CENTER_AREA_CSM),
    /**
     * 底边栏
     */
    BOTTOM_SIDEBAR('bottomSidebar', BOTTOM_SIDEBAR_CSM),
    /**
     * 底边栏
     */
    LEFT_SIDEBAR('leftSidebar', LEFT_SIDEBAR_CSM),
    ;

    /**
     * swingBuilder中保存的id
     */
    private String id
    /**
     * 关联的对象
     */
    private Consumer relationBean

    SwingKeyEnum(String id, Consumer relationBean) {
        this.id = id
        this.relationBean = relationBean
    }

    /**
     * 获得该控件
     */
    Object get() {
        return swingBuilder."$id"
    }

}