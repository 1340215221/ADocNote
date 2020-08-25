package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import com.rh.note.grammar.TitleGrammar;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级目录文件
 */
@Data
public class TwoLevelFile implements IAdocFile {

    private String filePath;
    private List<TitleGrammar> titles = new ArrayList<>();

}
