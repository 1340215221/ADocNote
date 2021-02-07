package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 更新include行内容 参数
 */
@Getter
@Setter
public class UpdateCaretLineAO implements BaseAO {
    /**
     * 文件项目路径
     */
    private String filePath;
    /**
     * 新的行内容
     */
    private String newLineContent;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(filePath) || StringUtils.isBlank(newLineContent);
    }
}
