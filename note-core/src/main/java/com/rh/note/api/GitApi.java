package com.rh.note.api;

import com.rh.note.ao.GitPullAO;
import com.rh.note.ao.GitPushAO;
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

    public void pull(GitPullAO ao) throws ApplicationException {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        GitUtil git = new GitUtil(ao.getAbsolutePath());
        try {
            git.init();
            git.add();
            git.commit();
            try {
                git.pull(ao.getProgressMonitor());
            } catch (Exception e) {
                git.reset(); // 自动合并失败时, 删除合并信息, 方便手动合并
                throw e;
            }
        } finally {
            git.close();
        }
    }

    public void push(GitPushAO ao) throws ApplicationException {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        GitUtil git = new GitUtil(ao.getAbsolutePath());
        try {
            git.init();
            git.add();
            git.commit();
            try {
                git.pull(ao.getProgressMonitor());
                git.push();
            } catch (Exception e) {
                git.reset(); // 自动合并失败时, 删除合并信息, 方便手动合并
                throw e;
            }
        } finally {
            git.close();
        }
    }
}
