package com.rh.note.vo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Writer;

/**
 * 写入流结果
 */
@RequiredArgsConstructor
public class WriterVO {

    /**
     * 写入流
     */
    @NonNull
    @Getter
    private Writer writer;
    @NonNull
    private Runnable runnable;

    /**
     * 关闭流
     */
    public void close() {
        runnable.run();
    }
}
