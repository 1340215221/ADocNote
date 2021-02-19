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
            try {
                git.pull();
            } catch (Exception e) {
                git.reset(); // 合并失败时, 删除合并信息, 方便手动合并
                throw e;
            }
        } finally {
            git.close();
        }
    }

    public void push(String absolutePath) throws ApplicationException {
        if (!FileUtil.isAbsolutePath(absolutePath) || !FileUtil.isDirectory(absolutePath)) {
            return;
        }
        GitUtil git = new GitUtil(absolutePath);
        try {
            git.init();
            git.add();
            git.commit();
            try {
                git.pull();
                git.push();
            } catch (Exception e) {
                git.reset(); // 合并失败时, 删除合并信息, 方便手动合并
                throw e;
            }
        } finally {
            git.close();
        }
    }
}
