package com.rh.note.util;

import com.rh.note.ao.TextPaneKeyStrokeAO;
import com.rh.note.exception.IsNotSyntaxSugarLineException;
import lombok.NonNull;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

/**
 * textPane快捷键活动 工具
 */
@Slf4j
public class KeymapAction implements Keymap {
    /**
     * 新生成的keymap
     */
    @Delegate
    private Keymap keymap;

    public KeymapAction(@NonNull String name, @NonNull Keymap keymap) {
        this.keymap = JTextComponent.addKeymap(name, keymap);
    }

    /**
     * 添加回车事件
     */
    public KeymapAction addEnterAction(@NonNull Consumer<TextPaneKeyStrokeAO> consumer) {
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new TextAction("textPane") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    consumer.accept(new TextPaneKeyStrokeAO(event));
                } catch (IsNotSyntaxSugarLineException e) {
                    new DefaultEditorKit.InsertBreakAction().actionPerformed(event);
                }
            }
        });
        return this;
    }

    /**
     * 添加上键事件
     */
    public KeymapAction addUpAction(@NonNull Consumer<TextPaneKeyStrokeAO> consumer) {
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new TextAction("menu_up") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    consumer.accept(new TextPaneKeyStrokeAO(event));
                } catch (Exception e) {
                    log.error("[添加上键事件] error", e);
                    DefaultEditorKitUtil.upAction(event);
                }
            }
        });
        return this;
    }

    /**
     * 添加下键事件
     */
    public KeymapAction addDownAction(@NonNull Consumer<TextPaneKeyStrokeAO> consumer) {
        keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new TextAction("menu_down") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    consumer.accept(new TextPaneKeyStrokeAO(event));
                } catch (Exception e) {
                    log.error("[添加下键事件] error", e);
                    DefaultEditorKitUtil.downAction(event);
                }
            }
        });
        return this;
    }

}
