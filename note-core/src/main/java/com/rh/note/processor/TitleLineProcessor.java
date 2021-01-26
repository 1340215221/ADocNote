package com.rh.note.processor;

import com.rh.note.common.ILineHandler;
import com.rh.note.line.TitleLine;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.TitleSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题行处理器
 */
@RequiredArgsConstructor
public class TitleLineProcessor implements ILineHandler {

    /**
     * 文件相对路径
     */
    @NonNull
    private String filePath;
    /**
     * 标题列表
     */
    private final List<TitleLine> titleLines = new ArrayList<>();

    @Override
    public void handle(int lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        TitleSyntax titleSyntax = new TitleSyntax().init(lineContent);
        if (titleSyntax == null) {
            return;
        }
        TitleBeanPath beanPath = new TitleBeanPath()
//                .setTitlePath() // todo
                .setLineNumber(lineNumber)
                .setFilePath(filePath);
        TitleLine titleLine = new TitleLine()
                .setTitleSyntax(titleSyntax)
//                .setParentTitle()
                .setBeanPath(beanPath);
        titleLines.add(titleLine);
    }
}
