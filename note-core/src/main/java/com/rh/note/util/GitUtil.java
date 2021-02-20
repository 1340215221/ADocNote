package com.rh.note.util;

import cn.hutool.core.io.FileUtil;
import com.rh.note.app.config.UserGitConfig;
import com.rh.note.common.BaseBuilder;
import com.rh.note.config.FrameLaunchConfig;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.EmptyCommitException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * git工具对象
 */
public class GitUtil implements BaseBuilder {

    private FrameLaunchConfig config;
    private UserGitConfig gitConfig = new UserGitConfig();
    private Git git;

    public GitUtil(String absolutePath) {
        config = FrameLaunchConfig.getInstance(absolutePath);
    }

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

    public void pull(ProgressMonitor progressMonitor) throws ApplicationException {
        try {
            // 不带密码更新
            PullCommand pull = git.pull();
            if (progressMonitor != null) {
                pull.setProgressMonitor(progressMonitor);
            }
            pull.setStrategy(MergeStrategy.SIMPLE_TWO_WAY_IN_CORE).call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.PULL_OPERATION_FAILED, e);
        }
    }

    public void close() {
        git.close();
    }

    public void reset() {
        try {
            git.reset().call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_MERGE_RECOVERY_FAILED, e);
        }
    }

    public void push() {
        try {
            PushCommand push = git.push();
            if (true) {
                // 添加提交密码
                push.setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitConfig.getUsername(), gitConfig.getPassword()));
            }
            push.setTimeout(60);
            push.call();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.GIT_PUSH_FAILED, e);
        }
    }
}
