package com.rh.note.exception;

/**
 * 初始化编辑区内容失败 异常<br/>
 * 需要的处理操作:<br/>
 * 销毁AdocTextPaneBuilder
 */
public class TextPaneInitContentException extends ApplicationException {

    public TextPaneInitContentException(Exception e) {
        super(ErrorCodeEnum.FAILED_TO_INITIALIZE_EDIT_AREA_CONTENT, e);
    }
}
