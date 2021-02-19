package com.rh.note.app.config;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class UserGitConfig {
    /**
     * 默认提交内容
     */
    private String commitMsg = "update";
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 提交目录
     */
    private List<String> submitDirectory = Arrays.asList("README.adoc", "adoc/");
}
