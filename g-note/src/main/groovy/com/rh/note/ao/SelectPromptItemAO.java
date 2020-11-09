package com.rh.note.ao;

/**
 * 选择提示内容 参数
 */
public class SelectPromptItemAO {

    /**
     * 操作类型
     * {@link SelectPromptItemAO.Type}
     */
    private Type type;

    public Type getType() {
        return type;
    }

    /**
     * 选择上一个
     */
    public SelectPromptItemAO up() {
        type = Type.UP;
        return this;
    }

    /**
     * 选择下一个
     */
    public SelectPromptItemAO down() {
        type = Type.DOWN;
        return this;
    }

    /**
     * 选择下一个
     */
    public boolean isNext() {
        return Type.DOWN.equals(type);
    }

    /**
     * 选择上一个
     */
    public boolean isPrevious() {
        return Type.UP.equals(type);
    }

    public boolean checkRequiredItems() {
        return type == null;
    }

    /**
     * 操作类型
     */
    public enum Type {
        /**
         * 上键
         */
        UP,
        /**
         * 下键
         */
        DOWN,
        ;
    }
}
