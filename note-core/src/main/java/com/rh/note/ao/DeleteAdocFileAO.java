package com.rh.note.ao;

import cn.hutool.core.io.FileUtil;
import com.rh.note.common.BaseAO;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除adoc文件参数
 */
@Getter
@Setter
public class DeleteAdocFileAO implements BaseAO {
    /**
     * 文件绝对路径
     */
    private String absolutePath;

    @Override
    public boolean checkMissRequiredParams() {
        return !FileUtil.isAbsolutePath(absolutePath);
    }
}
