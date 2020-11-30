package com.rh.note.constants;

/**
 * 基础常量
 */
public interface BaseConstants {

    /**
     * 包路径正则
     */
    String package_path_regex = "[a-zA-Z0-9\\.]+";
    /**
     * 项目标识正则
     */
    String pro_label_regex = "[a-zA-Z0-9\\-_]+";
    /**
     * 文件名正则
     */
    String file_name_regex = "[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-\\s+<>]+";
    /**
     * 文件路径正则
     */
    String file_path_regex = "[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-\\s\\\\/]+";

}
