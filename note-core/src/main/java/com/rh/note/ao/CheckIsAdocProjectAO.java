package com.rh.note.ao;

import cn.hutool.core.io.LineHandler;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
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

    /**
     * 查找根标题 处理器
     */
    public static class FindRootTitleHandlerImpl implements LineHandler {

        private boolean isFind = false;

        @Override
        public void handle(String lineText) {
            if (isFind || StringUtils.isBlank(lineText)) {
                return;
            }
            TitleLine titleLine = new TitleLine().init(lineText);
            if (titleLine == null || titleLine.getLevel() > 1) {
                throw new ApplicationException(ErrorCodeEnum.THE_READ_ME_FILE_DOES_NOT_HAVE_A_FIRST_LEVEL_TITLE);
            }
            isFind = true;
        }
    }
}
