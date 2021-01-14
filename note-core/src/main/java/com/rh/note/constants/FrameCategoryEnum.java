package com.rh.note.constants;

/**
 * 所属窗口
 */
public enum FrameCategoryEnum {
    /**
     * 废弃对象
     */
    ABANDONED {
        @Override
        public boolean match(FrameCategoryEnum frameCategoryEnum) {
            return this.equals(frameCategoryEnum);
        }
    },
    /**
     * 项目管理窗口
     */
    PRO_MANAGE {
        @Override
        public boolean match(FrameCategoryEnum frameCategoryEnum) {
            return this.equals(frameCategoryEnum) || UNIVERSAL.equals(frameCategoryEnum);
        }
    },
    /**
     * 工作窗口
     */
    WORK {
        @Override
        public boolean match(FrameCategoryEnum frameCategoryEnum) {
            return this.equals(frameCategoryEnum) || UNIVERSAL.equals(frameCategoryEnum);
        }
    },
    /**
     * 通用对象
     */
    UNIVERSAL {
        @Override
        public boolean match(FrameCategoryEnum frameCategoryEnum) {
            return this.equals(frameCategoryEnum) || PRO_MANAGE.equals(frameCategoryEnum) || WORK.equals(frameCategoryEnum);
        }
    },
    ;

    /**
     * 符合类型
     */
    public abstract boolean match(FrameCategoryEnum frameCategoryEnum);
}
