package com.rh.note.action;

import com.rh.note.api.FrameContextApi;
import com.rh.note.api.WorkViewApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工作窗口 入口
 */
@Slf4j
@Component
public class WorkAction {

    @Autowired
    private WorkViewApi workViewApi;
    @Autowired
    private FrameContextApi frameContextApi;

    /**
     * 关闭窗口
     */
    public void closeContext() {
        frameContextApi.closeContext();
    }
}
