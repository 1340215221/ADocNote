package com.rh.note.common;

import com.rh.note.line.IncludeLine;
import com.rh.note.path.IncludeBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 引用行处理器
 */
@RequiredArgsConstructor
public class IncludeLineProcessor implements ILineHandler {

    /**
     * 文件路径
     */
    @NonNull
    private String filePath;

    @Override
    public void handle(int lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        IncludeSyntax includeSyntax = new IncludeSyntax().init(lineContent);
        if (includeSyntax == null) {
            return;
        }
        IncludeBeanPath beanPath = new IncludeBeanPath()
                .setLineNumber(lineNumber)
                .setFilePath(filePath);
        IncludeLine includeLine = new IncludeLine()
                .setBeanPath(beanPath)
//                .setParentTitle()
                .setIncludeSyntax(includeSyntax);
    }
}
