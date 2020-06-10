package com.rh.note.action

import com.rh.note.constant.ErrorCodeEnum
import com.rh.note.entity.adoc.AdocTitile
import com.rh.note.exception.AdocException
import com.rh.note.factory.ActionFactory
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import javax.swing.*
import java.awt.*
import java.awt.event.KeyEvent
import java.util.regex.Pattern

class EditAreaActionImpl implements ISwingBuilder {

    /**
     * 全局保存
     */
    void saveOperation() {
        // 遍历所有已经打开的编辑区
        if (CollectionUtils.isEmpty(editTextAreaList)) {
            return
        }
        editTextAreaList.forEach { id ->
            if (StringUtils.isBlank(id)) {
                return
            }

            def textArea = swingBuilder."${id}" as JTextArea
            // 将编辑区内容写入到文件中
            String path = id.replaceFirst("edit_area_text_area_", "")
            File file = new File(path)
            if (!file.exists() || file.isDirectory()) {
                return
            }

            def fos = null
            def writer = null
            try {
                fos = new FileOutputStream(file)
                writer = new OutputStreamWriter(fos)
                textArea.write(writer)
            } catch (Exception e) {
                System.err.println("保存失败\n${e.message}\n${e.stackTrace.toString()}")
            } finally {
                writer?.flush()
                writer?.close()
                fos?.flush()
                fos?.close()
            }
        }
    }

    java.util.List<String> editTextAreaList = new ArrayList()

    void buildEditArea(AdocTitile adocTitile) {
        if (adocTitile == null) {
            return
        }

        String filePath = adocTitile.path
        // 创建文件滚动面板, 创建普通面板, 左 行号, 上 文件标签, 中 编辑区, 下 编辑信息 todo 上下区域应该不会随文件打开而多次创建
        def panel = swingBuilder.scrollPane(id: "${id}_scroll_${filePath}") {
            swingBuilder.panel(id: "${id}_panel_${filePath}",
                    layout: new BorderLayout(),
            ) {
                swingBuilder.panel(id: "${id}_west_${filePath}",
                        constraints: BorderLayout.WEST,
                        bounds: [0, 0, 20, 0],
                ) {}
                swingBuilder.panel(id: "${id}_center_${filePath}",
                        constraints: BorderLayout.CENTER,
                        layout: new BorderLayout(),
                ) {
                    swingBuilder.textArea(id: "${id}_text_area_${filePath}",
                            keyPressed: { KeyEvent event ->
                                // 创建文件
                                if (event.keyCode == 10 && event.modifiers == 0) {
                                    // 获取光标所在行内容
                                    def textArea = event.source as JTextArea
                                    def caret = textArea.caret
                                    def lineNumber = textArea.getLineOfOffset(caret.dot)
                                    def startOffset = textArea.getLineStartOffset(lineNumber)
                                    def endOfOffset = textArea.getLineEndOffset(lineNumber)
                                    def lineContent = textArea.getText(startOffset, endOfOffset - startOffset)  // 完整一行内容, 包括换行符

                                    if (StringUtils.isBlank(lineContent)) {
                                        return
                                    }
                                    // 判断是否为创建语法
                                    def matcher = Pattern.compile('^(\\s*)=>([1-9])\\s([\\u4e00-\\u9fa5a-zA-Z0-9_-]+)\\s*$').matcher(lineContent)
                                    if (!matcher.find()) {
                                        return
                                    }

                                    def indent = matcher.group(1)
                                    def level = matcher.group(2).toInteger()
                                    def fileName = matcher.group(3)
                                    if (StringUtils.isBlank(fileName)) {
                                        return
                                    }
                                    // 判断文件位置, 判断文件是否存在
                                    def textAreaId = textArea.name
                                    def directory = this.getIncludeFileDirectory(textAreaId.substring('edit_area_text_area_'.length(), textAreaId.length()))
                                    if (StringUtils.isBlank(directory)) {
                                        return
                                    }

                                    def substring = directory.substring(0, directory.length() - 1)
                                    def projectPath = substring.substring(0, substring.lastIndexOf(File.separator))
                                    def file = new File("${directory}${fileName}.adoc")
                                    if (file.exists()) {
                                        if (file.isDirectory()) {
                                            throw new AdocException(ErrorCodeEnum.file_creation_failed)
                                        } else {
                                            // 将快捷语法转换为adoc语法
                                            textArea.replaceRange("${indent}include::${file.path}[]", startOffset, endOfOffset)
                                            ActionFactory.action_factory.editAction.refreshFileList(projectPath)
                                            return
                                        }
                                    }
                                    // 创建文件
                                    def parentFile = file.parentFile
                                    if (!parentFile.exists()) {
                                        parentFile.mkdirs()
                                    }
                                    file.createNewFile()
                                    // 写入标题
                                    def writer = null
                                    try {
                                        writer = new FileWriter(file)
                                        writer.write("\n\n${'='*level} ${fileName}")
                                    }catch(Exception e){
                                        throw new AdocException(ErrorCodeEnum.file_write_failed, e)
                                    }finally{
                                        writer?.flush()
                                        writer?.close()
                                    }
                                    // 将快捷语法转换为adoc语法
                                    textArea.replaceRange("${indent}include::${file.path}[]", startOffset, endOfOffset)
                                    ActionFactory.action_factory.editAction.refreshFileList(projectPath)
                                }
                                // 修改include文件名
                                if (event.keyCode == 117 && event.modifiers == 1) {
                                    String newTitleName = JOptionPane.showInputDialog('请输入新标题')
                                    if (StringUtils.isBlank(newTitleName)) {
                                        return
                                    }
                                    // 修改指向的文件名和标题内容
                                    def textArea = event.source as JTextArea
                                    def caret = textArea.caret
                                    def line = textArea.getLineOfOffset(caret.dot)
                                    def startOffset = textArea.getLineStartOffset(line)
                                    def endOffset = textArea.getLineEndOffset(line)
                                    def lineContent = textArea.getText(startOffset, endOffset - startOffset)
                                    def matcher = Pattern.compile('^\\s*include::([^\\.]+)\\.(\\S+)\\[\\S*\\]$').matcher(lineContent)
                                    if (!matcher.find()) {
                                        return
                                    }

                                    def includePath = matcher.group(1) + '.adoc'
                                    def includeSuffix = matcher.group(2)
                                    if (!'adoc'.equals(includeSuffix)) {
                                        return
                                    }

                                    def includeFileName = includePath.substring(includePath.lastIndexOf(File.separator) + 1, includePath.length() - 5)
                                    def file = new File(includePath)
                                    def newFile = new File(includePath.replaceFirst(includeFileName, newTitleName))
                                    if (!file.renameTo(newFile)) {
                                        return
                                    }

                                    // 将已经修改的保存到文件
                                    def projectPath = this.getProjectPathOfOpenFile(adocTitile.path)
                                    ActionFactory.action_factory.editAction.saveOperation(projectPath)
                                    // 修改include指向文件标题
                                    def reader = new BufferedReader(new FileReader(newFile))
                                    boolean isFind = true
                                    def baos = new ByteArrayOutputStream()
                                    def osw = new OutputStreamWriter(baos)
                                    reader.transformLine(osw) { String lineStr ->
                                        if (isFind) {
                                            if (StringUtils.isBlank(lineStr)) {
                                                return lineStr
                                            }

                                            isFind = false
                                            if (lineStr.matches("^=+ ${includeFileName}\\s*")) {
                                                return lineStr.replaceFirst(includeFileName, newTitleName)
                                            }

                                            return lineStr
                                        }

                                        return lineStr
                                    }
                                    reader.close()
                                    baos.writeTo(new FileOutputStream(newFile))
                                    baos.flush()
                                    baos.close()
                                    // 关闭指向文件 edit_area_scroll_D:\my_program\java-note\README.adoc
                                    try {
                                        def panel = swingBuilder."edit_area_scroll_${includePath}" as JScrollPane
                                        def edit_area_panel = swingBuilder.edit_area as JPanel
                                        edit_area_panel.remove(panel)
                                    } catch (Exception e) {
                                    }
                                    // 修改include语句
                                    textArea.replaceRange(lineContent.replaceFirst(includeFileName, newTitleName), startOffset, endOffset)
                                    // 刷新文件列表
                                    ActionFactory.action_factory.editAction.refreshFileList(projectPath)
                                    ActionFactory.action_factory.editAction.saveOperation(projectPath)
                                }
                            }
                    ) {}
                }
                swingBuilder.panel(id: "${id}_south_${filePath}",
                        constraints: BorderLayout.SOUTH,
                        bounds: [0, 0, 0, 10],
                ) {}
            }
        }

        def edit_area_panel = swingBuilder.edit_area as JPanel
        edit_area_panel.add(panel, filePath)
        def layout = swingBuilder.edit_area_layout as CardLayout
        layout.last(edit_area_panel)

        if (edit_area_panel.componentCount == 1) {
            edit_area_panel.validate()
            edit_area_panel.repaint()
        }

        editTextAreaList.add("${id}_text_area_${filePath}")
    }

    /**
     * 获得导入文件目录
     */
    private String getIncludeFileDirectory(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null
        }

        // readme
        def readMeIndex = filePath.indexOf('README.adoc')
        if (readMeIndex > -1) {
            return "${filePath.substring(0, readMeIndex)}twoLevel${File.separator}"
        }

        // twoLevel
        def twoLevelIndex = filePath.indexOf('twoLevel')
        if (twoLevelIndex > -1) {
            return "${filePath.substring(0, twoLevelIndex)}content${File.separator}"
        }

        return null
    }

    /**
     * 获得打开文件的项目目录
     */
    private String getProjectPathOfOpenFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null
        }

        // readme
        def readMeIndex = filePath.indexOf('README.adoc')
        if (readMeIndex > -1) {
            return "${filePath.substring(0, readMeIndex)}"
        }

        // twoLevel
        def twoLevelIndex = filePath.indexOf('twoLevel')
        if (twoLevelIndex > -1) {
            return "${filePath.substring(0, twoLevelIndex)}"
        }

        // content
        def content = filePath.indexOf('content')
        if (content > -1) {
            return "${filePath.substring(0, content)}"
        }

        return null
    }

    private String getId() {
        return 'edit_area'
    }

    /**
     * 查找该文件是否已经打开
     */
    boolean findOpenedFile(String filePath) {
        try {
            def edit_area_scroll = swingBuilder."${id}_scroll_${filePath}"
            return true
        } catch (Exception e) {
            return false
        }
    }

    /**
     * 移动已打开文件到最上面
     */
    void showOpenedFile(String filePath) {
        def edit_area_panel = swingBuilder.edit_area as JPanel
        def layout = edit_area_panel.layout as CardLayout
        layout.show(edit_area_panel, filePath)
    }

    /**
     * 将文本写入到编辑控件中
     */
    void writeEditArea(AdocTitile adocTitile, File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return
        }

        def fis = null
        def isr = null
        try {
            fis = new FileInputStream(file)
            isr = new InputStreamReader(fis)

            def textArea = swingBuilder."edit_area_text_area_${adocTitile.path}" as JTextArea
            textArea.read(isr, null)
        } catch (Exception e) {
            System.err.println("打开文件失败\n${e.message}\n${e.stackTrace.toString()}")
        } finally {
            isr?.close()
            fis?.close()
        }
    }

    void refreshFileList() {
        def tree = swingBuilder.file_list_tree as JTree
        tree.validate()
        tree.repaint()
    }
}
