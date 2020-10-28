package com.rh.note.api;

import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.path.ProBeanPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;

import java.io.File;

/**
 * git操作
 */
@Slf4j
public class GitServiceApi {
    /**
     * 推送到远程分支
     */
    public void push() {
        // todo push 中可能出现冲突合并
    }

    /**
     * add和提交 adoc内容
     */
    public void commit(String msg) {
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }

        File file = new File(projectPath);
        if (!file.exists() || !file.isDirectory()) {
            return;
        }

        String commitMsg = StringUtils.isNotBlank(msg) ? msg : "update";
        try {
            Git git = Git.open(file);
            // twoLevel
            git.add().addFilepattern("adoc/twoLevel").call();
            // content
            git.add().addFilepattern("adoc/content/").call();
            git.commit().setMessage(commitMsg).call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_COMMIT_ERROR, e);
        }
        log.info("提交成功");
    }
}
