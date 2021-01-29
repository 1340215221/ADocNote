package com.rh.note.exception;

/**
 * 编辑区内容写入文件 异常<br/>
 *todo
 * 补偿处理:<br/>
 * 1. 将当前编辑区内容写入到临时文件中<br/>
 * -- 创建一个以当前文件名的目录, 里面在创建一个同名文件<br/>
 * -- 异步创建<br/>
 * 2. 用系统的文件管理器打开备份文件的目录
 */
public class TextPaneWriterToFileException extends ApplicationException {
    public TextPaneWriterToFileException(Throwable e) {
        super(ErrorCodeEnum.FAILED_TO_WRITE_THE_CONTENT_OF_THE_EDIT_AREA_TO_THE_FILE, e);
    }
}
