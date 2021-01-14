package com.rh.note.view;

import com.rh.note.builder.ProListBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.vo.ProItemVO;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 项目列表 视图
 */
public class ProListView extends BaseView<ProListBuilder, JList<ProItemVO>> {

    public @NotNull ProListView init() {
        return super.init();
    }
}
