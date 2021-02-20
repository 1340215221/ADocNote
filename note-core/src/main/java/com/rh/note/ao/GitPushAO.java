package com.rh.note.ao;

import cn.hutool.core.io.FileUtil;
import com.rh.note.bean.ProgressMonitorImpl;
import com.rh.note.common.BaseAO;
import com.rh.note.common.IShowProgress;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jgit.lib.ProgressMonitor;

/**
 * git推送 参数
 */
@Getter
public class GitPushAO implements BaseAO {
    /**
     * 绝对路径
     */
    @Setter
    private String absolutePath;
    /**
     * git进度监控器
     */
    private ProgressMonitor progressMonitor;

    @Override
    public boolean checkMissRequiredParams() {
        return !FileUtil.isAbsolutePath(absolutePath) || !FileUtil.isDirectory(absolutePath);
    }

    public void copy(IShowProgress showProgress) {
        if (showProgress == null) {
            return;
        }
        progressMonitor = new ProgressMonitorImpl(showProgress);
    }
}
