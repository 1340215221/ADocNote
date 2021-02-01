package com.rh.note.ao;

import cn.hutool.core.io.FileUtil;
import com.rh.note.common.BaseAO;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 创建adoc文件 参数
 */
public class CreateAdocFileAO implements BaseAO {

    /**
     * 绝对路径
     */
    @Getter
    private String absolutePath;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(absolutePath) || !FileUtil.isAbsolutePath(absolutePath)
                || !"adoc".equalsIgnoreCase(FileUtil.extName(absolutePath));
    }

    public void copy(IncludeSyntax syntax) {
        if (syntax == null) {
            return;
        }
        String includePath = syntax.getIncludePath();
        // todo
    }
}
