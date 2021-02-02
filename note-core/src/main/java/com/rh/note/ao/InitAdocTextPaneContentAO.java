package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.vo.GenerateIncludeSyntaxVO;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 初始化adoc文件内容
 */
@Getter
public class InitAdocTextPaneContentAO implements BaseAO {
    /**
     * 初始化内容
     */
    private String initContent;
    /**
     * 文件项目路径
     */
    private String filePath;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(initContent) || StringUtils.isBlank(filePath);
    }

    public void copy(GenerateIncludeSyntaxVO vo) {
        if (vo == null) {
            return;
        }
        initContent = vo.getInitAdocFileContent();
        filePath = vo.getTargetFilePath();
    }
}
