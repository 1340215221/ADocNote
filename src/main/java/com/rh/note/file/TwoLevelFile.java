package com.rh.note.file;

import com.rh.note.grammar.TitleGrammar;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级目录文件
 */
@Data
public class TwoLevelFile {

    private String absolutePath;
    private List<TitleGrammar> titles = new ArrayList<>();

}
