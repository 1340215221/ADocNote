package com.rh.note.common;

import com.rh.note.file.AdocFile;
import com.rh.note.file.ProjectDirectory;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;

import java.util.List;

/**
 * 文件接口
 */
public interface IAdocFile {

    /**
     * 获得文件绝对路径
     */
    default String getAbsolutePath() {
        return new ProjectDirectory().getAbsolutePath() + getFilePath();
    }

    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 设置文件路径
     */
    <T extends IAdocFile> T setFilePath(String filePath);

    /**
     * 复制属性
     */
    default void copy(AdocFile adocFile) {}

    /**
     * 将adoc文件中的标题内容提取为一个新文件
     */
    default IAdocFile generateAdocFileByTitle(TitleGrammar titleGrammar) {
        //todo
        return null;
    }

    /**
     * 获得一个标题下所有的include信息
     */
    default List<IncludeGrammar> getIncludeOfTitle(TitleGrammar titleGrammar) {
        //todo
        return null;
    }

    /**
     * 将标题和标题内容转为include语句
     */
    default void title2Include(TitleGrammar titleGrammar, String filePath) {
        //todo
    }
}
