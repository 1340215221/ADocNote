package com.rh.note.builder;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.common.BaseBuilder;
import com.rh.note.component.TextScrollPane;

/**
 * 编辑区滚动面板
 */
@ComponentBean(builders = {AdocTextPaneBuilder.class, JavaTextPaneBuilder.class})
public interface TextScrollPaneBuilder extends BaseBuilder {

    String scroll_pane_id = "scroll_pane_{}";

    default TextScrollPane getScrollPane() { return null; }
}
