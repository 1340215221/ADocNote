package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import com.rh.note.grammar.TitleGrammar;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * readMe文件
 */
@Data
public class ReadMeFile implements IAdocFile {

    /**
     * 文件绝对地址
     */
    private String filePath = "README.adoc";
    /**
     * 根标题
     */
    private TitleGrammar rootTitle;

    public ReadMeFile init() {
        return this;
    }

    @Override
    public void copy(AdocFile adocFile) {
        if (adocFile == null) {
            return;
        }
        Optional.ofNullable(adocFile.getTitleGrammars())
                .filter(CollectionUtils::isNotEmpty)
                .map(list -> list.get(0))
                .ifPresent(this::setRootTitle);
    }
}
