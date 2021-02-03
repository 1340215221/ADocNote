package com.rh.note.exception;

/**
 * 停止查找根标题 异常
 */
public class StopFindRootTitleException extends ApplicationException {
    public StopFindRootTitleException() {
        super(ErrorCodeEnum.STOP_FIND_ROOT_TITLE);
    }
}
