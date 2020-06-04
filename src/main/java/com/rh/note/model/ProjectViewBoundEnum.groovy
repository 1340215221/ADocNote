package com.rh.note.model

enum ProjectViewBoundEnum implements Bound {

    project_frame{
        @Override
        protected void settingDefaultConfig() {
            w = 550
            h = 400
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