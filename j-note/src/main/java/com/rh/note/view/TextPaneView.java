package com.rh.note.view;

import com.rh.note.base.ITitleBeanPath;
import com.rh.note.base.Init;
import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.ProBeanPath;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.vo.WriterVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Function;

/**
 * 编辑区视图
 */
@Slf4j
public class TextPaneView extends Init<AdocTextPane> {

    public static void create(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }
        new TextPaneBuilder(beanPath).init();
    }

    public @Nullable TextPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.id(filePath));
    }

    private @NotNull AdocTextPane textPane() {
        return getBean();
    }

    /**
     * 编辑区内容是否为空
     */
    public boolean isBlank() {
        return textPane().getDocument().getLength() < 1;
    }

    /**
     * 设置显示内容
     */
    public void setText(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }
        try (InputStream is = new FileInputStream(beanPath.getAbsolutePath()); Reader read = new InputStreamReader(is)) {
            textPane().read(read, null);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.READ_FILE_ERROR, e);
        }
    }

    /**
     * 获得标题, 通过光标所在行内容
     */
    public @Nullable TitleBeanPath getTitleByCaretLineContent() {
        return new ParsingCareLineApi().getTitleByCaretLineContent();
    }

    /**
     * 解析文件为简单adoc对象
     */
    private @NotNull  AdocFile getSimpleAdocFile() {
        AdocFileBeanPath beanPath = (AdocFileBeanPath) textPane().getBeanPath();
        return AdocFile.getInstanceAndNotChildren(beanPath.getFilePath());
    }

    /**
     * 解析光标行
     */
    private int getCareLineNumber() {
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        return rootElement.getElementIndex(dot);
    }

    /**
     * 编辑区内容写入到文件
     */
    public void write(Function<String, WriterVO> getFileWriterFunction) {
        if (getFileWriterFunction == null) {
            return;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        String filePath = ((AdocFileBeanPath) textPane().getBeanPath()).getFilePath();
        WriterVO vo = getFileWriterFunction.apply(projectPath + filePath);
        if (vo == null) {
            return;
        }
        try {
            textPane().write(vo.getWriter());
            vo.close();
        } catch (Exception e) {
            log.error("[将编辑区内容写入到文件 失败], filePath={}", filePath, e);
        }
    }

    private class ParsingCareLineApi {
        /**
         * 获得标题, 通过光标所在行内容
         */
        public @Nullable TitleBeanPath getTitleByCaretLineContent() {
            int lineNumber = getCareLineNumber() + 1;
            if (lineNumber < 1) {
                return null;
            }
            AdocFile adocFile = getSimpleAdocFile();
            TitleLine titleLine = adocFile.getTitleByLineNumber(lineNumber);
            if (titleLine == null) {
                return null;
            }
            return titleLine.getBeanPath();
        }

    }
}
