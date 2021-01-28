package com.rh.note.path;

import com.rh.note.common.BaseFileConfig;
import com.rh.note.common.IArgsConstructorBean;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 文件对象路径
 */
@Data
@Accessors(chain = true)
public class FileBeanPath extends BaseFileConfig implements IArgsConstructorBean {
    /**
     * 文件项目路径
     * todo 应该校验他是非空的
     */
    private String filePath;

    @Override
    public @NotNull String[] getBeanNameArgs() {
        return new String[]{filePath};
    }

    /**
     * 获得绝对路径
     * todo 可以创建时就保存就来, 确定是非空的, 也不用实现 BaseFileConfig
     */
    public @NotNull String getAbsolutePath() {
        return getProPath() + filePath;
    }
}
