package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import com.rh.note.grammar.TitleGrammar;
import lombok.Data;

/**
 * 内容文件
 */
@Data
public class ContentFile implements IAdocFile {
    private String filePath;
    private String fileName;
    private TitleGrammar parentTitle;
}
