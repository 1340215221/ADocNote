package com.rh.note.ao;

import com.rh.note.constants.FrameCategoryEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 加载容器参数
 */
@Getter
@RequiredArgsConstructor
public class LoadContextAO {
    /**
     * 窗口类型
     */
    @NonNull
    private FrameCategoryEnum frameCategoryEnum;
}
