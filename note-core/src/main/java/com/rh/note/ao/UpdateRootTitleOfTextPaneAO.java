package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 更新adoc编辑区根节点名字 参数
 */
@Getter
@Setter
public class UpdateRootTitleOfTextPaneAO implements BaseAO {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 新的标题名
     */
    private String newTitleName;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(filePath) || StringUtils.isBlank(newTitleName);
    }
}
