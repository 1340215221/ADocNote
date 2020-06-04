package com.rh.note.model

enum MainViewBoundEnum implements Bound {

    main_frame{
        @Override
        protected void settingDefaultConfig() {
            w = 900
            h = 600
            x = (screenWidth - w) / 2
            y = (screenHight - h) / 2
        }
    },
    head_menu{
        @Override
        protected void settingDefaultConfig() {
            w = main_frame.w
            h = 50
            x = 0
            y = 0
        }
    },
    bottom_sidebar{
        @Override
        protected void settingDefaultConfig() {
            w = main_frame.w + 500
            h = 20
            x = 0
            y = 0
        }
    },
    left_sidebar{
        @Override
        protected void settingDefaultConfig() {
            w = 15
            h = main_frame.h - head_menu.h - bottom_sidebar.h - frameTitleHight
            x = 0
            y = 0
        }
    },
    edit_area{
        @Override
        protected void settingDefaultConfig() {
            w = main_frame.w - left_sidebar.w
            h = main_frame.h - head_menu.h - bottom_sidebar.h - frameTitleHight
            x = 0
            y = 0
        }

        @Override
        protected void refreshBound() {
            this.settingDefaultConfig()
        }
    },
    open_file_title{
        @Override
        protected void settingDefaultConfig() {
            w = edit_area.w
            h = 20
            x = 0
            y = 0
        }
    },
    line_num_sidebar{
        @Override
        protected void settingDefaultConfig() {
            w = 40
            h = edit_area.h - open_file_title.h
            x = 0
            y = open_file_title.h
        }
    },
    edit_file_content{
        @Override
        protected void settingDefaultConfig() {
            w = edit_area.w - line_num_sidebar.w
            h = edit_area.h - open_file_title.h
            x = 0
            y = 0
        }
    },
    ;
    protected static final Integer screenWidth = 1920
    protected static final Integer screenHight = 1080
    protected static final Integer frameTitleHight = 30
    protected Integer x
    protected Integer y
    protected Integer w
    protected Integer h

    /**
     * 设置默认配置
     */
    protected abstract void settingDefaultConfig()

    /**
     * 刷新大小
     * 用于重新计算位置大小
     */
    protected void refreshBound() {
        this.settingDefaultConfig()
    }

    @Override
    List<Integer> getBound() {
        this.refreshBound()
        return [x, y, w, h]
    }

    static void initAllBound() {
        values().sort { it.ordinal() }
                .each { it.settingDefaultConfig() }
    }
}
