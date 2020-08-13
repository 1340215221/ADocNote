package com.rh.note.file;

import com.rh.note.grammar.TitleGrammar;
import lombok.Data;

/**
 * 内容文件
 */
@Data
public class ContentFile {
    private String absolutePath;
    private String fileName;
    private TitleGrammar parentTitle;
}
