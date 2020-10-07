package com.rh.note.file;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * adoc文件
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdocFile {

    @NonNull
    private String filePath;

    public static @Nullable AdocFile getInstance(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return new AdocFile(filePath).init();
    }

    /**
     * 解析文件
     */
    private @Nullable AdocFile init() {
        return newFile(filePath).each(new MatchLine());
    }

    /**
     * 遍历文件内容
     */
    private @NotNull IForEach newFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return empty_each;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return empty_each;
        }
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
            return matchLine -> {
                if (matchLine == null) {
                    return null;
                }
                MutableInt lineNumber = new MutableInt(0);
                br.lines().forEach(lineContent -> {
                    lineNumber.increment();
                    matchLine.match(new LineContent()
                            .setNumber(lineNumber.getValue())
                            .setText(lineContent));
                });
                return this;
            };
        } catch (Exception e) {
            log.error("[文件读取失败], filePath={}", filePath, e);
            return empty_each;
        }
    }

    private static final IForEach empty_each = forEach -> null;

    interface IForEach {
        @Nullable AdocFile each(IMatchLine forEach);
    }

    interface IMatchLine {
        void match(LineContent lineContent);
    }

    class MatchLine implements IMatchLine {
        @Override
        public void match(LineContent lineContent) {
            if (lineContent == null) {
                return;
            }
            //todo
        }
    }

    @Data
    class LineContent {
        private int number;
        private String text;
    }

}
