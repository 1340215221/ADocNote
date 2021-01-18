package com.rh.note.view;

import com.rh.note.builder.ProListBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.vo.ProItemVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 项目列表 视图
 */
public class ProListView extends BaseView<ProListBuilder, JList<ProItemVO>> {

    public @NotNull ProListView init() {
        return super.init(null);
    }

    /**
     * 获得被选择的项目地址
     */
    public @Nullable String getSelectProPath() {
        ProItemVO vo = list().getSelectedValue();
        if (vo == null || StringUtils.isBlank(vo.getProjectPath())) {
            return null;
        }
        return vo.getProjectPath();
    }

    protected JList<ProItemVO> list() {
        return super.getComponent(ProListBuilder::getList);
    }
}
