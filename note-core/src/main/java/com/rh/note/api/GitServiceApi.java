package com.rh.note.api;

import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.path.ProBeanPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.merge.MergeStrategy;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * git操作
 */
@Slf4j
@Component
public class GitServiceApi {
    /**
     * 推送到远程分支
     */
    public void push() {
        // todo push 中可能出现冲突合并
    }

    /**
     * 更新
     */
    public void pull() {
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }

        File file = new File(projectPath);
        if (!file.exists() || !file.isDirectory()) {
            return;
        }

        try {
            Git git = Git.open(file);
            git.pull().setStrategy(MergeStrategy.RECURSIVE).call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_PULL_ERROR);
        }
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
            git.add().addFilepattern("README.adoc")
                    .addFilepattern("adoc/twoLevel")
                    .addFilepattern("adoc/content/")
                    .call();
            git.commit().setMessage(commitMsg).setAllowEmpty(false).call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_COMMIT_ERROR, e);
        }
        log.info("提交成功");
    }
}
