package com.rh.note.ao;

import com.rh.note.base.BaseLine;
import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.file.AdocFile;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TextLine;
import com.rh.note.line.TitleLine;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.IncludeSyntaxSugar;
import com.rh.note.syntax.TitleSyntax;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 解析语法参数接口
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SyntaxAnalysisAO implements ISyntaxAnalysisAO {
    /**
     * 行对象
     */
    private TextLine textLine;

    /**
     * 创建
     */
    public static SyntaxAnalysisAO create(TextLine textLine) {
        SyntaxAnalysisAO ao = new SyntaxAnalysisAO();
        if (textLine != null) {
            ao.textLine = textLine;
        }
        return ao;
    }

    @Override
    public boolean matchInclude() {
        return textLine != null && new IncludeSyntaxSugar().init(textLine.getText()) != null;
    }

    /**
     * 生成include块
     */
    public IncludeLine generateIncludeLine() {
        IncludeSyntaxSugar sugar = new IncludeSyntaxSugar().init(textLine.getText());
        if (sugar == null) {
            return null;
        }

        // 指向文件
        TitleSyntax targetTitleSyntax = new TitleSyntax();
        targetTitleSyntax.setLevel(sugar.getLevel()); // todo 引用文件级别应该是父标题级别减一
        targetTitleSyntax.setTitleName(sugar.getTitleName());

        TitleLine targetTitleLine = new TitleLine();
        targetTitleLine.setTitleSyntax(targetTitleSyntax);
        targetTitleLine.setLineNumber(3);
        targetTitleLine.setParentTitle(textLine.getParentTitle());

        AdocFile targetAdocFile = new AdocFile().init(targetTitleLine);
        targetTitleLine.setAdocFile(targetAdocFile);
        targetAdocFile.setFilePath(AdocFileTypeEnum.matchByFilePath(textLine.getAdocFile().getFilePath()).getFilePathOfNextDirectory()
                + sugar.getTitleName() + ".adoc");

        // 当前文件
        IncludeSyntax includeSyntax = new IncludeSyntax();
        includeSyntax.setIndented(sugar.getIndented());
        includeSyntax.setTargetFileName(sugar.getTitleName());
        includeSyntax.setTargetFileSuf("adoc");
        includeSyntax.setTargetRelativePath(AdocFileTypeEnum.matchByFilePath(textLine.getAdocFile().getFilePath()).getRelativePathOfNextDirectory()
                + sugar.getTitleName() + ".adoc");


        IncludeLine includeLine = new IncludeLine();
        includeLine.setIncludeSyntax(includeSyntax);
        includeLine.setTargetFile(targetAdocFile);
        includeLine.setAdocFile(textLine.getAdocFile());
        includeLine.setLineNumber(textLine.getLineNumber());
        includeLine.setParentTitle(textLine.getParentTitle());
        return includeLine;
    }
}
