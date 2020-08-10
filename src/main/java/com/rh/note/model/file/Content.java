package com.rh.note.model.file;

import lombok.Data;

/**
 * 内容文件
 */
@Data
public class Content {
    private String absolutePath;
    private String fileName;
    private Title parentTitle;
}
