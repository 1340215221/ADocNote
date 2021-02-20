package com.rh.note.bean;

import com.rh.note.common.IShowProgress;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.lib.BatchingProgressMonitor;

@RequiredArgsConstructor
public class ProgressMonitorImpl extends BatchingProgressMonitor {

    @NonNull
    private IShowProgress showProgress;

    @Override
    protected void onUpdate(String taskName, int workCurr) {
        showProgress.show(taskName, 0);
    }

    @Override
    protected void onEndTask(String taskName, int workCurr) {
        showProgress.show(taskName, 100);
    }

    @Override
    protected void onUpdate(String taskName, int workCurr, int workTotal, int percentDone) {
        showProgress.show(taskName, percentDone);
    }

    @Override
    protected void onEndTask(String taskName, int workCurr, int workTotal, int percentDone) {
        showProgress.show(taskName, percentDone);
    }
}
