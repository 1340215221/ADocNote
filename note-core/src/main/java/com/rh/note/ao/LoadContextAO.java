package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.constants.FrameCategoryEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 加载容器参数
 */
@Data
@Accessors(chain = true)
public class LoadContextAO implements BaseAO {
    /**
     * 窗口类型
     */
    private FrameCategoryEnum frameCategoryEnum;
    /**
     * 容器配置对象
     */
    private Object contextConfig;

    @Override
    public boolean checkMissRequiredParams() {
        return frameCategoryEnum == null;
    }
}
