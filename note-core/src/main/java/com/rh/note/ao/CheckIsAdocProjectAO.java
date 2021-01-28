package com.rh.note.ao;

import cn.hutool.core.io.LineHandler;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
import com.rh.note.syntax.TitleSyntax;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 校验是adoc项目
 */
@Getter
@RequiredArgsConstructor
public class CheckIsAdocProjectAO {
    /**
     * 项目地址
     */
    @NonNull
    private String proPath;

    public ClickedProjectListAO copyTo() {
        return new ClickedProjectListAO().setProPath(proPath);
    }

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
    public static class FindRootTitleHandlerImpl implements LineHandler {
        /**
         * 是否找到根标题
         */
        private boolean isFind = false;

        @Override
        public void handle(String lineText) {
            if (isFind || StringUtils.isBlank(lineText)) {
                return;
            }
            TitleSyntax titleSyntax = new TitleSyntax().init(lineText);
            if (titleSyntax == null || titleSyntax.getLevel() > 1) {
                throw new ApplicationException(ErrorCodeEnum.THE_READ_ME_FILE_DOES_NOT_HAVE_A_FIRST_LEVEL_TITLE);
            }
            isFind = true;
        }
    }
}
