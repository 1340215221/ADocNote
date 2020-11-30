package com.rh.note.view;

import com.rh.note.ao.MarkLineAO;
import com.rh.note.ao.SelectCaretLineAO;
import com.rh.note.base.Init;
import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.UnknownLogicException;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.ProBeanPath;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.IncludeJavaSyntax;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.util.ViewUtil;
import com.rh.note.vo.WriterVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Caret;
import javax.swing.text.Element;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 转换
     */
    public static @Nullable TextPaneView cast(AdocTextPane textPane) {
        if (textPane == null) {
            return null;
        }
        AdocFileBeanPath beanPath = (AdocFileBeanPath) textPane.getBeanPath();
        String filePath = beanPath.getFilePath();
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return new TextPaneView().init(filePath);
    }

    /**
     * 获得所有被编辑过的文件路径
     */
    public static @NotNull List<String> getAllEditedFilePath() {
        return ViewUtil.getComponentsByClass(AdocTextPane.class)
                .map(textPane -> textPane.getBeanPath().getBeanPath())
                .collect(Collectors.toList());
    }

    /**
     * 关闭, 通过文件路径
     */
    public static void deleteByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }

        ViewUtil.removeByComponentId(TextPaneBuilder.id(filePath));
    }

    @Override
    public @Nullable TextPaneView init(String filePath) {
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
    public void writeFileText(AdocFileBeanPath beanPath) {
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
     * 获得标题路径对象, 通过光标所在行内容
     */
    public @Nullable TitleBeanPath getTitleBeanPathByCaretLineContent() {
        return new ParsingCareLineApi().getTitleBeanPathByCaretLineContent();
    }

    /**
     * 解析文件为简单adoc对象
     */
    private @NotNull AdocFile getSimpleAdocFile() {
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

    /**
     * 获得光标所在行内容
     * 不包含换行符
     */
    public @Nullable String getCaretLineContent() {
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return null;
        }
        Element element = rootElement.getElement(index);
        try {
            return textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            log.error("[获取行内容 失败], filePath={}, lineNumber={}", ((AdocFileBeanPath) textPane().getBeanPath()).getFilePath(), index + 1);
            return null;
        }
    }

    /**
     * 选择光标所在行
     * 通过光标行起始位置, 定位选择的范围
     */
    public void selectCaretLine(@NonNull SelectCaretLineAO ao) {
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return;
        }
        Element element = rootElement.getElement(index);
        int startOffset = element.getStartOffset() + ao.getStartOffset();
        textPane().select(startOffset, startOffset + ao.getLength());
    }

    /**
     * 选择光标所在行
     */
    public void selectCaretLine() {
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return;
        }
        Element element = rootElement.getElement(index);
        textPane().select(element.getStartOffset(), element.getEndOffset() - 1);
    }

    /**
     * 获得被选择文本内容
     */
    public @Nullable String getSelectedText() {
        return textPane().getSelectedText();
    }

    /**
     * 替换被选择内容
     */
    public void replaceSelectedText(String text) {
        if (text == null) {
            return;
        }
        textPane().replaceSelection(text);
    }

    /**
     * 获得文件路径
     */
    public @NotNull String getFilePath() {
        AdocFileBeanPath beanPath = (AdocFileBeanPath) textPane().getBeanPath();
        return beanPath.getFilePath();
    }

    /**
     * 选择根标题名
     * 如果没有找到需要抛异常
     */
    public void selectRootTitleName() throws UnknownLogicException {
        TitleLine rootTitle = getSimpleAdocFile().getRootTitle();
        if (rootTitle == null) {
            throw new UnknownLogicException();
        }
        Element element = textPane().getDocument().getDefaultRootElement().getElement(rootTitle.getLineNumber() - 1);
        if (element == null) {
            throw new UnknownLogicException();
        }
        TitleSyntax syntax = rootTitle.getTitleSyntax();
        int startOffsetOfLine = syntax.getStartOffsetOfTitleName();
        int length = syntax.getLengthOfTitleName();
        int startOffset = element.getStartOffset() + startOffsetOfLine;
        textPane().select(startOffset, startOffset + length);
    }

    /**
     * 获得标题, 通过光标所在行内容
     */
    public TitleLine getSimpleTitleByCaretLineContent() {
        return new ParsingCareLineApi().getTitleByCaretLineContent();
    }

    /**
     * 选择行, 通过范围
     * 选择时, 不包含最后一个换行符
     */
    public void selectLineByRange(int startLineIndex, int endLineIndex) {
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        Element startElement = rootElement.getElement(startLineIndex);
        if (startElement == null) {
            return;
        }

        Element endElement = Integer.MAX_VALUE == endLineIndex ?
                rootElement.getElement(rootElement.getElementCount() - 1) :
                rootElement.getElement(endLineIndex);
        if (endElement == null) {
            return;
        }

        textPane().select(startElement.getStartOffset(), endElement.getEndOffset());
    }

    /**
     * 获得文件内容
     */
    public @Nullable String getText() {
        return textPane().getText();
    }

    /**
     * 获得光标坐标
     */
    public @Nullable Point getCaretPoint() {
        Caret caret = textPane().getCaret();
        if (!caret.isVisible()) {
            caret.setVisible(true);
        }
        return caret.getMagicCaretPosition();
    }

    /**
     * 请求焦点
     */
    public void requestFocus() {
        textPane().requestFocus();
    }

    /**
     * todo
     * 替换提示内容
     */
    public void replacePromptItem(int index1, String value) {
        if (index1 < 0 || StringUtils.isBlank(value)) {
            return;
        }
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int elementIndex = rootElement.getElementIndex(dot);
        Element element = rootElement.getElement(elementIndex);
        if (element == null) {
            return;
        }
        int start = element.getStartOffset() + index1 + 1;
        int end = dot;
        textPane().select(start, end);
        textPane().replaceSelection(value);
    }

    /**
     * todo
     * 替换提示内容
     */
    public void replacePromptItem2(int index1, String value) {
        if (index1 < 0 || StringUtils.isBlank(value)) {
            return;
        }
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int elementIndex = rootElement.getElementIndex(dot);
        Element element = rootElement.getElement(elementIndex);
        if (element == null) {
            return;
        }
        int start = element.getStartOffset() + index1;
        int end = dot;
        textPane().select(start, end);
        System.out.println("__" + textPane().getSelectedText() + "__");
        textPane().replaceSelection(value);
        System.out.println(value);
    }

    /**
     * todo
     * 改变引用java行
     */
    public void changeIncludeJavaLine(MarkLineAO ao) {
        if (ao == null || ao.checkRequiredItems()) {
            return;
        }
        // 获得行号
        MutableInt lineNumber = new MutableInt(0);
        IncludeJavaSyntax javaSyntax = Arrays.stream(textPane().getText().split("\n"))
                .peek(e -> lineNumber.increment())
                .map(lineContent -> {
                    IncludeJavaSyntax syntax = new IncludeJavaSyntax().init(lineContent);
                    if (syntax == null) {
                        return null;
                    }
                    if (!syntax.getTargetRelativePath().equals(ao.getIncludeFilePath())) {
                        return null;
                    }
                    return syntax;
                }).filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (javaSyntax == null) {
            return;
        }
        // 修改内容
        if (ao.isCtrlOne()) {
            javaSyntax.updateStartLine(ao.getLineNumber());
        }
        if (ao.isCtrlTwo()) {
            javaSyntax.updateEndLine(ao.getLineNumber());
        }
        String includeStr = javaSyntax.toString();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        Element element = rootElement.getElement(lineNumber.getValue() - 1);
        if (element == null) {
            return;
        }
        int totalLineNumber = rootElement.getElementCount();
        // 如果非最后一行, 不选择行尾的换行符
        if (totalLineNumber == lineNumber.getValue()) {
            textPane().select(element.getStartOffset(), element.getEndOffset());
        } else {
            textPane().select(element.getStartOffset(), element.getEndOffset() - 1);
        }
        replaceSelectedText(includeStr);
    }

    private class ParsingCareLineApi {

        /**
         * 获得标题, 通过光标所在行内容
         */
        public @Nullable TitleLine getTitleByCaretLineContent() {
            int lineNumber = getCareLineNumber() + 1;
            if (lineNumber < 1) {
                return null;
            }
            AdocFile adocFile = getSimpleAdocFile();
            return adocFile.getTitleByLineNumber(lineNumber);
        }

        /**
         * 获得标题路径, 通过光标所在行内容
         */
        public @Nullable TitleBeanPath getTitleBeanPathByCaretLineContent() {
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
