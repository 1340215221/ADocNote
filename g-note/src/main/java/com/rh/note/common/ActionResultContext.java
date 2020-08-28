package com.rh.note.common;

/**
 * 操作结果上下文
 */
public class ActionResultContext {

    private static final ThreadLocal<ActionResult> action_result = new ThreadLocal<>();

    /**
     * 获取上下文中的
     */
    public ActionResult getResult() {
        return action_result.get();
    }

}
