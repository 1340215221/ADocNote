package com.rh.note.view.bound;

import lombok.Data;

import java.util.List;

/**
 * 基础区域属性
 */
@Data
public abstract class BaseBound {

    /**
     * 是否隐藏
     */
    private Boolean hideStatus;

    /**
     * 获得区域
     */
    public List<Integer> getBound() {
        // todo
        return null;
    }

}
