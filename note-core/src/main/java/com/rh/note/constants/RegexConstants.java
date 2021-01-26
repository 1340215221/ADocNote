package com.rh.note.constants;

/**
 * 正则常量
 */
public interface RegexConstants {

    /**
     * 支持文件路径
     */
    String file_path_regex = "[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-+<>/]+";
    /**
     * 支持的文件名
     */
    String file_name_regex = "[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-+<>]+";
    /**
     * 支持标题名
     */
    String title_name_regex = "[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-+<>]+"
            + "|[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-+<>]+\\s+[0-9a-zA-Z\\u4e00-\\u9fa5_,\\.\\-+<>]+";
    /**
     * int数字正则
     */
    String int_number_regex = "^[0-9]+$";
}
