package com.rh.note.grammar;

import com.rh.note.common.IAdocFile;
import com.rh.note.common.IGrammar;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.constant.ProjectStructureEnum;
import com.rh.note.exception.NoteException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class IncludeGrammar implements IGrammar {

    /**
     * 指向标题
     */
    private TitleGrammar targetTitle;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 引用文件路径
     */
    private String targetFilePath;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 标题级别
     * todo 级别放在这里不合适, 应该放到指向的标题对象里
     */
    private Integer level;
    /**
     * 缩进空白字符
     */
    private String indent;
    /**
     * 行号
     */
    private Integer lineNumber;

    public IncludeGrammar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }

        Matcher matcher = Pattern.compile("^\\s*include::((?:\\.\\.|adoc)[\\\\/][a-zA-Z\u4e00-\u9fa5\\\\/]+)\\.([a-zA-Z0-9]+)\\[(lines[0-9\\.]+){0,1}\\]$").matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }

        targetFilePath = matcher.group(1) + ".adoc"; // todo 待处理 {} 变量
        fileSuffix = matcher.group(2);
        return this;
    }

    /**
     * 获得主标题名字
     */
    public String getTitleName() {
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length() - 5);
    }

    /**
     * 初始化，通过当前行是否为include简单语法
     */
    public IncludeGrammar initByGrammar(String lineContent, String filePath) {
        if (StringUtils.isBlank(lineContent) || StringUtils.isBlank(filePath)) {
            return null;
        }
        Matcher matcher = Pattern.compile("^(\\s*)=>([1-9])\\s([\\u4e00-\\u9fa5a-zA-Z0-9_-]+)\\s*$").matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        indent = matcher.group(1);
        level = Integer.valueOf(matcher.group(2));
        targetFilePath = this.generateTargetFilePath(matcher.group(3), filePath);
        this.filePath = filePath;
        return this;
    }

    /**
     * 生成指向文件的完整路径
     */
    private String generateTargetFilePath(String fileName, String filePath) {
        if (StringUtils.isBlank(fileName) || StringUtils.isBlank(filePath)) {
            return null;
        }
        ProjectStructureEnum parentStructure = Arrays.stream(ProjectStructureEnum.values())
                .filter(e -> e.match(filePath))
                .findFirst()
                .orElseThrow(() -> new NoteException(ErrorMessage.PARAMETER_ERROR));
        return parentStructure.getChildrenPath() + fileName + ".adoc";
    }

    /**
     * 生成语法语句
     */
    public String generateGrammar() {
        String targetFilePath = ProjectStructureEnum.generateIncludeTargetPath(this.targetFilePath);
        return indent + "include::" + targetFilePath + "[]";
    }

    /**
     * 获得指向的adoc文件对象
     */
    public IAdocFile buildSimpleTargetAdocFile() {
        return ProjectStructureEnum.buildSimpleAdocFile(getTargetFilePath());
    }

    public TitleGrammar copyTo() {
        TitleGrammar titleGrammar = new TitleGrammar()
                .setLevel(level)
                .setFilePath(targetFilePath)
                .setName(getTargetTitleName());
        return titleGrammar;
    }

    /**
     * 获得指向文件标题名, 通过指向文件路径
     */
    private String getTargetTitleName() {
        if (StringUtils.isBlank(targetFilePath)) {
            return null;
        }

        Matcher matcher = Pattern.compile("^[\\.\\\\/\\u4e00-\\u9fa5a-zA-Z0-9_-]+[\\\\/]([\\u4e00-\\u9fa5a-zA-Z0-9_-]+)\\.adoc$").matcher(targetFilePath);
        if (!matcher.find()) {
            return null;
        }
        return matcher.group(1);
    }

    @Override
    public String toLineContent() {
        //todo
        return null;
    }
}
