package com.rh.note.ao;

import com.rh.note.base.BaseLine;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TextLine;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.IncludeSyntaxSugar;
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
        if (textLine == null) {
            return null;
        }
        SyntaxAnalysisAO ao = new SyntaxAnalysisAO();
        ao.textLine = textLine;
        return ao;
    }

    @Override
    public boolean matchInclude() {
        return new IncludeSyntaxSugar().init(textLine.getText()) != null;
    }

    /**
     * 生成include块
     */
    public IncludeLine generateIncludeLine() {
        IncludeSyntaxSugar sugar = new IncludeSyntaxSugar().init(textLine.getText());
        if (sugar == null) {
            return null;
        }

        IncludeSyntax includeSyntax = new IncludeSyntax();
        includeSyntax.copy(sugar);

        new IncludeLine()
                .setIncludeSyntax(sugar)
                .setTargetFile()
    }
}
