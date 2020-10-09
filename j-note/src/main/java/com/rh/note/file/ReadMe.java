package com.rh.note.file;

import com.rh.note.line.TitleLine;
import lombok.experimental.Delegate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReadMe {

    @Delegate
    private AdocFile adocFile;

    public static @Nullable ReadMe getInstance() {
        AdocFile adocFile = AdocFile.getInstance("README.adoc");
        if (adocFile == null) {
            return null;
        }
        ReadMe readMe = new ReadMe();
        readMe.adocFile = adocFile;
        return readMe;
    }

    /**
     * 获得标题, 通过文件路径
     */
    public @Nullable TitleLine getTitleByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        TitleLine rootTitle = adocFile.getRootTitle();
        Deque<TitleLine> deque =  new LinkedList<>();
        deque.push(rootTitle);
        while (true) {
            TitleLine titleLine = deque.pop();
            if (titleLine == null) {
                return null;
            }
            if (titleLine.getFilePath().equals(filePath)) {
                return titleLine;
            }
            List<TitleLine> childrenTitles = titleLine.getChildrenTitles();
            if (CollectionUtils.isEmpty(childrenTitles)) {
                continue;
            }
            childrenTitles.forEach(deque::push);
        }
    }
}
