package com.rh.note.ao;

import cn.hutool.core.io.LineHandler;
import com.rh.note.common.BaseAO;
import com.rh.note.exception.StopFindRootTitleException;
import com.rh.note.syntax.TitleSyntax;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验是adoc项目
 */
@Getter
public class CheckIsAdocProjectAO implements BaseAO {
    /**
     * 项目地址
     */
    @Setter
    private String proPath;
    /**
     * 是否找到根标题
     */
    private boolean isFind = false;

    @Override
    public boolean checkMissRequiredParams() {
        return StringUtils.isBlank(proPath);
    }

    /**
     * 该处理器在找到结果后会抛出 StopFindRootTitleException 异常<br/>
     * 结果在 isFind 字段中
     */
    public LineHandler getLineHandler() {
        return new FindRootTitleHandlerImpl();
    }

    public String getReadMeFilePath() {
        if (StringUtils.isBlank(proPath)) {
            return "";
        }
        return proPath.endsWith("/") ? proPath + "README.adoc" : proPath + "/" + "README.adoc";
    }

    /**
     * 查找根标题 处理器
     */
    private class FindRootTitleHandlerImpl implements LineHandler {

        @Override
        public void handle(String lineText) {
            if (StringUtils.isBlank(lineText)) {
                return;
            }
            TitleSyntax titleSyntax = new TitleSyntax().init(lineText);
            if (titleSyntax != null && titleSyntax.getLevel() == 1) {
                isFind = true;
            }
            throw new StopFindRootTitleException();
        }
    }
}
