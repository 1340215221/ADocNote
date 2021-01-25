package com.rh.note.ao;

import com.rh.note.constants.FrameCategoryEnum;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 加载容器参数
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class LoadContextAO {
    /**
     * 窗口类型
     */
    @NonNull
    private final FrameCategoryEnum frameCategoryEnum;
    /**
     * 容器配置对象
     */
    private Object contextConfig;
}
