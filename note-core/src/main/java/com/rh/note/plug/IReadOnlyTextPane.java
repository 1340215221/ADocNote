package com.rh.note.plug;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.util.LambdaUtil;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Keymap;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 只读编辑区
 */
public interface IReadOnlyTextPane {
    /**
     * 编辑相关action key
     */
    List<String> list = Arrays.asList(
            DefaultEditorKit.insertContentAction,
            DefaultEditorKit.deletePrevCharAction,
            DefaultEditorKit.deleteNextCharAction,
            DefaultEditorKit.deletePrevWordAction,
            DefaultEditorKit.deleteNextWordAction,
            DefaultEditorKit.cutAction,
            DefaultEditorKit.pasteAction,
            DefaultEditorKit.insertBreakAction,
            DefaultEditorKit.defaultKeyTypedAction,
            DefaultEditorKit.insertTabAction,
            DefaultEditorKit.writableAction
    );

    /**
     * 清除编辑功能
     */
    default void setReadOnly() {
        if (!(this instanceof JTextPane)) {
            return;
        }
        JTextPane textPane = (JTextPane) this;
        // 清除 textPane.actionMap.parent.parent 中的编辑action
        list.forEach(e ->
                textPane.getActionMap().getParent().getParent().remove(e));
        ActionMap parent = textPane.getActionMap().getParent();
        // 替换 textPane.actionMap.parent.keymap
        Field field = ReflectUtil.getField(parent.getClass(), "keymap");
        // 创建默认keymap
        Class clazz = LambdaUtil.hiddenExceptionSup(() ->
                Class.forName("javax.swing.text.JTextComponent$DefaultKeymap"));
        Constructor constructor = ReflectUtil.getConstructor(clazz, String.class, Keymap.class);
        Object instance = LambdaUtil.hiddenExceptionSup(() ->
                constructor.newInstance("BasicTextPaneUI", null));
        // 修改textPane默认keymap
        ReflectUtil.setFieldValue(parent, field, instance);
    }
}
