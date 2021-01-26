package com.rh.note.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.mutable.MutableInt;
import com.rh.note.common.BaseFileConfig;
import com.rh.note.common.ILineHandler;
import com.rh.note.common.IncludeLineProcessor;
import com.rh.note.processor.TitleLineProcessor;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * readme文件
 */
public class ReadMeFile extends BaseFileConfig {

    /**
     * readme文件路径
     */
    @NotNull
    @Getter
    private final String filePath = initFilePath();

    public @Nullable ReadMeFile init() {
        return FileUtil.isFile(filePath) ? this : null;
    }

    /**
     * 读取文件内容
     */
    public void readFile(TitleLineProcessor titleProcessor, IncludeLineProcessor includeProcessor) {
        FileUtil.readUtf8Lines(new File(filePath), new LineHandlerImpl(titleProcessor, includeProcessor));
    }

    private @Nullable String initFilePath() {
        if (StringUtils.isBlank(getProPath())) {
            return null;
        }
        return getProPath() + "README.adoc";
    }

    private static class LineHandlerImpl implements LineHandler {
        // 行内容处理器
        private List<ILineHandler> handlerList;
        // 行号
        private MutableInt lineNumber = new MutableInt(0);

        public LineHandlerImpl(ILineHandler... handlers) {
            handlerList = Arrays.asList(handlers);
        }

        @Override
        public void handle(String line) {
            lineNumber.increment();
            if (CollectionUtils.isEmpty(handlerList)) {
                return;
            }
            handlerList.forEach(handler -> handler.handle(lineNumber.intValue(), line));
        }
    }
}
