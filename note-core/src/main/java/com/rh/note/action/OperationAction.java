package com.rh.note.action;

import com.rh.note.api.FileApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.config.KeymapConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;

@Component
public class OperationAction {

    @Autowired
    private KeymapConfig keymap;
    @Autowired
    private WorkViewApi workViewApi;
    @Autowired
    private ProManageViewApi proManageViewApi;
    @Autowired
    private FileApi fileApi;

    /**
     * 判断是ctrl + 鼠标左键
     */
    public boolean isCtrlLeftClick(MouseEvent event) {
        return keymap.ctrlLeftClick(event);
    }

    /**
     * 判断是双击
     */
    public boolean isDoubleClick(MouseEvent mouseEvent) {
        return keymap.doubleClick(mouseEvent);
    }
}
