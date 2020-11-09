package com.rh.note.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.Action;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * textPane默认操作
 */
public class DefaultEditorKitUtil {

    /**
     * 默认上键操作
     */
    private static Action upAction;
    /**
     * 默认下键操作
     */
    private static Action downAction;

    static {
        InitAction.initUpAction();
        InitAction.initDownAction();
    }

    /**
     * 默认上键操作
     */
    public static void upAction(ActionEvent event) {
        if (event == null) {
            return;
        }
        upAction.actionPerformed(event);
    }

    /**
     * 默认下键操作
     */
    public static void downAction(ActionEvent event) {
        if (event == null) {
            return;
        }
        downAction.actionPerformed(event);
    }

    /**
     * 初始化类
     */
    private interface InitAction {

        static void initUpAction() {
            Action[] actions = new DefaultEditorKit().getActions();
            if (ArrayUtils.isEmpty(actions)) {
                return;
            }
            upAction = Arrays.stream(actions)
                    .filter(action -> DefaultEditorKit.upAction.equals(action.getValue(Action.NAME)))
                    .findFirst()
                    .orElseThrow(UnknownError::new);
        }

        static void initDownAction() {
            Action[] actions = new DefaultEditorKit().getActions();
            if (ArrayUtils.isEmpty(actions)) {
                return;
            }
            downAction = Arrays.stream(actions)
                    .filter(action -> DefaultEditorKit.downAction.equals(action.getValue(Action.NAME)))
                    .findFirst()
                    .orElseThrow(UnknownError::new);
        }
    }

}
