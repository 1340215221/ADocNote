package com.rh.note.config;

import com.rh.note.action.IWorkAction;
import com.rh.note.ao.ISyntaxAnalysisAO;
import com.rh.note.ao.SyntaxAnalysisAO;
import com.rh.note.base.BaseLine;
import com.rh.note.file.AdocFile;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TitleLine;

/**
 * 工作窗口入口--实现类配置
 */
public interface WorkActionBeanClassConfig extends IWorkAction<AdocFile, TitleLine, IncludeLine, BaseLine, SyntaxAnalysisAO> {
}
