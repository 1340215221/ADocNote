package com.rh.note.model.file;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级目录文件
 */
@Data
public class TwoLevel {

    private String absolutePath;
    private List<Title> titles = new ArrayList<>();

}
