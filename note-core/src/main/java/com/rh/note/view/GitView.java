package com.rh.note.view;

import com.rh.note.common.BaseView;
import com.rh.note.util.GitUtil;
import org.jetbrains.annotations.NotNull;

/**
 * todo git操作者
 */
public class GitView extends BaseView<GitUtil, GitUtil> {

    public @NotNull GitView init() {
        return super.init();
    }

    protected @NotNull GitUtil git() {
        return super.getComponent(GitUtil::getUtil);
    }

    public void pull() {
        // add .
        git().add();
        // commit -m 'update'
        git().commit();
        // fetch
        // pull
        git().pull();
    }
}
