package com.rh.note.factory;

import com.rh.note.view.*;
import groovy.lang.Closure;

public interface IMainViewFactory {

    default Object editFileContent(Closure run) {
        return new EditFileContent().init(run);
    }

    default Object openFileTitle(Closure run) {
        return new OpenFileTitle().init(run);
    }

    default Object lineNumSidebar(Closure run) {
        return new LineNumSidebar().init(run);
    }

    default Object editArea(Closure run) {
        return new EditArea().init(run);
    }

    default Object leftSidebar(Closure run) {
        return new LeftSidebar().init(run);
    }

    default Object bottomSidebar(Closure run) {
        return new BottomSidebar().init(run);
    }

    default Object headMenu(Closure run) {
        return new HeadMenu().init(run);
    }

    default Object basePanel(Closure run) {
        return new BasePanel().init(run);
    }

    default Object mainFrame(Closure run) {
        return new MainFrame().init(run);
    }

    default void leftTitlePanel(Closure closure) {
        new LeftTitlePanel().init(closure);
    }

    default void fileListTitleButton(Closure closure) {
        new FileListTitleButton().init(closure);
    }

    default void fileList(Closure closure) {
        new FileList().init(closure);
    }
}
