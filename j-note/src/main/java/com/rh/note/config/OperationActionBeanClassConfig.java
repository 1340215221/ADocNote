package com.rh.note.config;

import com.rh.note.action.IOperationAction;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;

/**
 * 用户操作实现类配置
 */
public interface OperationActionBeanClassConfig extends IOperationAction<AdocFile, TitleLine> {
}
