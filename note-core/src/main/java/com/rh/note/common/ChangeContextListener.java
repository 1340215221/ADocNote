package com.rh.note.common;

/**
 * 编辑区内容改变监听
 */
public interface ChangeContextListener {
    void run(String filePath);
}
