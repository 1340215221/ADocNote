package com.rh.note.path;

import com.rh.note.common.IArgsConstructorBean;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 文件对象路径
 */
@Data
@Accessors(chain = true)
public class FileBeanPath implements IArgsConstructorBean {
    /**
     * 文件项目路径
     * todo 应该校验他是非空的
     */
    private String filePath;

    @Override
    public @NotNull String[] getBeanNameArgs() {
        return new String[]{filePath};
    }
}
