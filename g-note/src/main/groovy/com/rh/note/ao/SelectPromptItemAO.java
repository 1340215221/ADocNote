package com.rh.note.ao;

import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * 选择提示内容 参数
 */
public class SelectPromptItemAO {

    /**
     * 操作类型
     * {@link SelectPromptItemAO.Type}
     */
    private Type type;

    public SelectPromptItemAO copy(KeyEvent event) {
        if (event == null) {
            return null;
        }
        Type type = Type.match(event);
        if (type == null) {
            return null;
        }
        this.type = type;
        return this;
    }

    /**
     * 操作类型
     */
    public enum Type {
        /**
         * 上键
         */
        UP(0) {
            @Override
            public boolean parsing(KeyEvent event) {
                return event != null && event.getKeyCode() == 38 && event.getModifiers() == 0;
            }
        },
        /**
         * 下键
         */
        DOWN(1) {
            @Override
            public boolean parsing(KeyEvent event) {
                return event != null && event.getKeyCode() == 40 && event.getModifiers() == 0;
            }
        },
        ;
        private Integer value;
        Type(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }
        public abstract boolean parsing(KeyEvent event);
        /**
         * 匹配类型, 通过按键
         */
        public static Type match(KeyEvent event) {
            return Arrays.stream(values())
                    .filter(e -> e.parsing(event))
                    .findFirst()
                    .orElse(null);
        }
    }
}
