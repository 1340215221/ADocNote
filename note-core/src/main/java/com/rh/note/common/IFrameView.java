package com.rh.note.common;

import com.rh.note.util.ViewContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.JFrame;

/**
 * 窗口 视图
 */
public abstract class IFrameView<B extends IFrameBuilder, C extends JFrame> extends ISingletonView<B, C> {

    /**
     * 关闭窗口
     */
    protected void dispose() {
        ApplicationContext app = ViewContextUtil.context.get();
        if (!(app instanceof AnnotationConfigApplicationContext)) {
            return;
        }
        ((AnnotationConfigApplicationContext) app).close();
    }

}
