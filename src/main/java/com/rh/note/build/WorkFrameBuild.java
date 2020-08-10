package com.rh.note.build;

import com.rh.note.view.*;
import groovy.lang.Closure;

/**
 * 构建工作窗口
 */
public interface WorkFrameBuild {

    default void workFrame(Closure children) {
        new WorkFrame().init(children);
    }

    default void basePanel(Closure children) {
        new BasePanel().init(children);
    }

    default void headMenu(Closure children) {
        new HeadMenu().init(children);
    }

    default void bottomSidebar(Closure children) {
        new BottomSidebar().init(children);
    }

    default void leftSidebar(Closure children) {
        new LeftSidebar().init(children);
    }

    default void leftTitlePanel(Closure children) {
        new LeftTitlePanel().init(children);
    }

    default void fileListTitleButton(Closure children) {
        new FileListTitleButton().init(children);
    }

    default void titleList(Closure children) {
        new TitleList().init(children);
    }

    default void editArea(Closure children) {
        new EditArea().init(children);
    }
}
