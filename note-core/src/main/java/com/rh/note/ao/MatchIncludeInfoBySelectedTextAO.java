package com.rh.note.ao;

import com.rh.note.path.ProBeanPath;
import com.rh.note.syntax.IncludeSyntaxSugar;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * 创建include指向文件 参数
 */
@Getter
@Accessors(chain = true)
public class MatchIncludeInfoBySelectedTextAO implements ICreateFileAndInitContentAO {
    /**
     * 文件绝对
     */
    private String absolutePath;
    /**
     * 文件内容
     */
    private String text;
    /**
     * 生成include文本
     */
    @Setter
    private String includeText;

    public @NotNull MatchIncludeInfoBySelectedTextAO setIncludeSyntaxSugar(IncludeSyntaxSugar syntaxSugar) {
        text = generateNewAdocFileContent(syntaxSugar);
        return this;
    }

    /**
     * 通过include语法对象生成新adoc文件内容
     */
    private @Nullable String generateNewAdocFileContent(IncludeSyntaxSugar syntaxSugar) {
        if (syntaxSugar == null) {
            return null;
        }
        StringBuilder str = new StringBuilder()
                .append("\n")
                .append("\n")
                .append(syntaxSugar.getIndented());
        Stream.generate(() -> "=").limit(syntaxSugar.getLevel()).forEach(str::append);
        str.append(" ")
                .append(syntaxSugar.getTitleName())
                .append("\n")
                .append("\n");
        return str.toString();
    }

    /**
     * 通过项目路径生成绝对路径
     */
    public @NotNull MatchIncludeInfoBySelectedTextAO setFilePath(String filePath) {
        if (StringUtils.isNotBlank(filePath)) {
            absolutePath = new ProBeanPath().getProjectPath() + filePath;
        }
        return this;
    }
}
