package com.rh.note.util;

import cn.hutool.core.io.FileUtil;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.app.config.UserGitConfig;
import com.rh.note.common.BaseBuilder;
import com.rh.note.config.FrameLaunchConfig;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.EmptyCommitException;
import org.eclipse.jgit.merge.MergeStrategy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * git工具对象
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class GitUtil implements BaseBuilder {

    @Autowired
    private FrameLaunchConfig config;
    private UserGitConfig gitConfig = new UserGitConfig();
    private Git git;

    @PostConstruct
    public void init() {
        if (!FileUtil.isAbsolutePath(config.getProPath()) || !FileUtil.isDirectory(config.getProPath())) {
            throw new ApplicationException(ErrorCodeEnum.GIT_OPERATION_OBJECT_CREATION_FAILED);
        }
        try {
            git = Git.open(new File(config.getProPath()));
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_OPERATION_OBJECT_CREATION_FAILED, e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (git != null) {
            git.close();
        }
    }

    public @NotNull GitUtil getUtil() {
        return this;
    }

    /**
     * 添加
     */
    public void add() throws ApplicationException {
        AddCommand add = git.add();
        gitConfig.getSubmitDirectory().forEach(add::addFilepattern);
        try {
            add.call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_ADD_OPERATION_FAILED, e);
        }
    }

    public void commit() throws ApplicationException {
        try {
            git.commit().setMessage(gitConfig.getCommitMsg()).setAllowEmpty(false).call();
        } catch (EmptyCommitException e) {
            // 没有要提交的内容
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.COMMIT_OPERATION_FAILED, e);
        }
    }

    public void pull() throws ApplicationException {
        try {
            // 不带密码提交
            git.pull().setStrategy(MergeStrategy.SIMPLE_TWO_WAY_IN_CORE).call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.PULL_OPERATION_FAILED, e);
        }
    }
}
