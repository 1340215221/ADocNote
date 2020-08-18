package com.rh.note.view;

import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * 异常弹窗
 */
public class ExceptionDialogRunView {
    /**
     * 系统错误异常msg
     */
    public static final String error_msg = "系统繁忙, 请等待";
    /**
     * 未知异常msg
     */
    public static final String operation_failed = "操作失败";
    /**
     * 关键业务参数错误
     */
    public static final String found_system_vulnerabilities = "发现系统漏洞";

    /**
     * 弹窗显示错误信息
     */
    public static void create(String errorMsg) {
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = error_msg;
        }
        JOptionPane.showMessageDialog(null, errorMsg);
    }

}
