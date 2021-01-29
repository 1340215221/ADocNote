package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.common.IFileBeanPath;
import com.rh.note.exception.RequestParamsValidException;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;

import java.awt.event.ActionEvent;

/**
 * 编辑区按键 参数
 */
@Getter
public class TextPaneKeyStrokeAO implements BaseAO {
    /**
     * 文件路径
     */
    private FileBeanPath beanPath;

    public TextPaneKeyStrokeAO(ActionEvent event) {
        if (event == null || !(event.getSource() instanceof IFileBeanPath)) {
            return;
        }
        beanPath = ((IFileBeanPath) event.getSource()).getBeanPath();
    }

    @Override
    public void assertThrow() throws RequestParamsValidException {
        if (beanPath == null) {
            throw new RequestParamsValidException();
        }
    }
}
