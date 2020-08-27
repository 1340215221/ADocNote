package com.rh.note.config;

import com.rh.note.builder.BasePanelBuilder;
import com.rh.note.builder.BottomSidebarBuilder;
import com.rh.note.builder.EditAreaBuilder;
import com.rh.note.builder.FileListTitleButtonBuilder;
import com.rh.note.builder.HeadMenuBuilder;
import com.rh.note.builder.LeftSidebarBuilder;
import com.rh.note.builder.LeftTitlePanelBuilder;
import com.rh.note.builder.TitleListBuilder;
import com.rh.note.builder.WorkFrameBuilder;
import groovy.lang.Closure;

/**
 * 构建工作窗口
 */
public interface IWorkFrame {

    default void workFrame(Closure children) {
        new WorkFrameBuilder().init(children);
    }

    default void basePanel(Closure children) {
        new BasePanelBuilder().init(children);
    }

    default void headMenu(Closure children) {
        new HeadMenuBuilder().init(children);
    }

    default void bottomSidebar(Closure children) {
        new BottomSidebarBuilder().init(children);
    }

    default void leftSidebar(Closure children) {
        new LeftSidebarBuilder().init(children);
    }

    default void leftTitlePanel(Closure children) {
        new LeftTitlePanelBuilder().init(children);
    }

    default void fileListTitleButton(Closure children) {
        new FileListTitleButtonBuilder().init(children);
    }

    default void titleList(Closure children) {
        new TitleListBuilder().init(children);
    }

    default void editArea(Closure children) {
        new EditAreaBuilder().init(children);
    }
}
