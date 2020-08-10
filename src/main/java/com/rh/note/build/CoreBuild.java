package com.rh.note.build;

import com.rh.note.api.FileAPIService;
import com.rh.note.service.AdocService;
import com.rh.note.service.FileService;

/**
 * 核心对象构建
 */
public interface CoreBuild extends ActionBuild {

    /**
     * 构建核心对象
     */
    static void build() {
        FileService fileService = new FileService();
        AdocService adocService = new AdocService();
        FileAPIService fileAPIService = new FileAPIService(fileService, adocService);

        workAction.setFileAPIService(fileAPIService);
        projectListAction.setFileAPIService(fileAPIService);
    }

}
