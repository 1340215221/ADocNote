package com.rh.note.action

import com.rh.note.entity.adoc.AdocTitile
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import javax.swing.*
import java.awt.*

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
            } catch (Exception e){
                System.err.println("保存失败\n${e.message}\n${e.stackTrace.toString()}")
            }finally{
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
                    swingBuilder.textArea(id: "${id}_text_area_${filePath}") {}
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
        }catch(Exception e){
            System.err.println("打开文件失败\n${e.message}\n${e.stackTrace.toString()}")
        }finally{
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
