package com.rh.note.collection;

import com.rh.note.common.IReadTitleLine;
import com.rh.note.common.ReadTitleLineImpl;
import com.rh.note.line.TitleLine;
import lombok.experimental.Delegate;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 读取adoc文件标题 集合
 */
public class ReadTitleLineList implements List<IReadTitleLine> {

    @Delegate
    private final List<IReadTitleLine> list = new ArrayList<>();

    /**
     * 获得集合中的根标题
     */
    public @Nullable ReadTitleLineImpl getRootTitleImpl() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return (ReadTitleLineImpl) list.stream()
                .filter(title -> title instanceof ReadTitleLineImpl)
                .min(Comparator.comparing(IReadTitleLine::getLineNumber))
                .orElse(null);
    }

    /**
     * 获得集合中的根标题
     */
    public @Nullable TitleLine getRootTitleLine() {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return (TitleLine) list.stream()
                .filter(title -> title instanceof TitleLine)
                .min(Comparator.comparing(IReadTitleLine::getLineNumber))
                .orElse(null);
    }
}
