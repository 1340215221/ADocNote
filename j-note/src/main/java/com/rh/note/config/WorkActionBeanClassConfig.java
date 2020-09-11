package com.rh.note.config;

import com.rh.note.action.IWorkAction;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;

/**
 * 工作窗口入口--实现类配置
 */
public interface WorkActionBeanClassConfig extends IWorkAction<AdocFile, TitleLine> {
}
