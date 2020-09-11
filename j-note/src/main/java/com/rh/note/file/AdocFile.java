package com.rh.note.file;

import com.rh.note.bean.IAdocFile;
import com.rh.note.line.BlankLine;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TextLine;
import com.rh.note.line.TitleLine;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * adoc文件对象
 */
@Data
public class AdocFile implements IAdocFile {

    /**
     * 文件项目路径
     */
    private String filePath;
    /**
     * 标题内容
     */
    private List<TitleLine> titleLines;
    /**
     * include内容
     */
    private List<IncludeLine> includeLines;
    /**
     * 空白内容
     */
    private List<BlankLine> blankLines;
    /**
     * 普通文本
     */
    private List<TextLine> textLines;

    /**
     * 初始化,通过项目相对路径
     */
    public AdocFile init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        //todo
        return null;
    }

}
