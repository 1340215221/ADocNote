package com.rh.note.ao;

import com.rh.note.line.TitleLine;
import com.rh.note.path.ProBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public class TitleContentAO implements ITitleContentAO{

    /**
     * 文件地址
     */
    private String filePath;
    /**
     * 文件创建 参数
     */
    private final CreateFileAndInitContentAO createFileAO = new CreateFileAndInitContentAO();
    /**
     * include行内容
     */
    private String includeLineText;

    public void copy(TitleLine titleLine) {
        setIncludeLineText(titleLine);
        setTargetAbsolutePath(titleLine);

        filePath = titleLine.getFilePath();
    }

    /**
     * 指向文件绝对路径
     */
    private void setTargetAbsolutePath(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }

        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }

        String filePath = titleLine.getAdocFile().getIncludeFileProjectPath();
        createFileAO.absolutePath = projectPath + filePath + titleLine.getTitleName() + ".adoc";
    }

    /**
     * 标题转换为include行内容
     */
    private void setIncludeLineText(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }

        IncludeSyntax includeSyntax = new IncludeSyntax();
        includeSyntax.setIndented("");
        includeSyntax.setTargetFileSuf("adoc");
        includeSyntax.setTargetFileName(titleLine.getTitleName());

        String targetRelativePath = titleLine.getAdocFile().getIncludeFileRelativeDirectory();
        includeSyntax.setTargetRelativePath(targetRelativePath + titleLine.getTitleName() + ".adoc");

        includeLineText = includeSyntax.toString();
    }

    /**
     * 是否为根标题
     */
    public boolean isRootTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return false;
        }

        return titleLine.isRootTitle();
    }

    /**
     * 获得标题内容范围
     */
    public @Nullable LineRangeAO getTitleContentRange(TitleLine titleLine) {
        if (titleLine == null) {
            return null;
        }

        return titleLine.getAdocFile().getTitleContentRange(titleLine.getBeanPath());
    }

    /**
     * 创建指向文件 参数
     */
    public static class CreateFileAndInitContentAO implements ICreateFileAndInitContentAO {

        /**
         * 文件绝对路径
         */
        @Getter
        private String absolutePath;
        /**
         * 文件初始化内容
         */
        private final StringBuilder text = new StringBuilder();

        /**
         * 文件初始化内容
         */
        public String getText() {
            return text.toString();
        }

        /**
         * 加两个空白行
         */
        public @NotNull CreateFileAndInitContentAO addTwoBlankLine() {
            text.append("\n\n");
            return this;
        }

        /**
         * 追加文件内容
         */
        public @NotNull CreateFileAndInitContentAO addText(String text) {
            if (StringUtils.isNotBlank(text)) {
                this.text.append(text);
            }
            return this;
        }
    }
}
