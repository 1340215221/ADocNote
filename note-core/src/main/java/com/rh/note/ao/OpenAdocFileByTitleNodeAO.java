package com.rh.note.ao;

import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 打开adoc文件,通过标题节点 参数
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenAdocFileByTitleNodeAO {

    /**
     * 文件项目路径
     */
    @NotNull
    private FileBeanPath beanPath;

    public static @Nullable OpenAdocFileByTitleNodeAO copy(TitleLine titleLine) {
        if (titleLine == null || titleLine.getBeanPath() == null || StringUtils.isBlank(titleLine.getBeanPath().getFilePath())) {
            return null;
        }
        OpenAdocFileByTitleNodeAO ao = new OpenAdocFileByTitleNodeAO();
        ao.beanPath = new FileBeanPath().setFilePath(titleLine.getBeanPath().getFilePath());
        return ao;
    }

    public @Nullable String getFilePath() {
        return beanPath.getFilePath();
    }
}
