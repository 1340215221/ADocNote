package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import com.rh.note.exception.ApplicationException;
import com.rh.note.util.GitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * git 接口
 */
@Slf4j
@Component
public class GitApi {

    public void pull(String absolutePath) throws ApplicationException {
        if (!FileUtil.isAbsolutePath(absolutePath) || !FileUtil.isDirectory(absolutePath)) {
            return;
        }
        GitUtil git = new GitUtil(absolutePath);
        try {
            git.init();
            git.add();
            git.commit();
            git.pull();
        } finally {
            git.close();
        }
    }
}
