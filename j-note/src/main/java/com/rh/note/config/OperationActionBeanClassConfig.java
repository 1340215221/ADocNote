package com.rh.note.config;

import com.rh.note.action.IOperationAction;
import com.rh.note.ao.SyntaxAnalysisAO;
import com.rh.note.base.BaseLine;
import com.rh.note.file.AdocFile;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TitleLine;

/**
 * 用户操作实现类配置
 */
public interface OperationActionBeanClassConfig extends IOperationAction<AdocFile, TitleLine, IncludeLine, BaseLine, SyntaxAnalysisAO> {
}
