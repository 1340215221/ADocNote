package com.rh.note.api

import com.rh.note.factory.WorkFrameFactory
import com.rh.note.model.component.EditAreaImpl
import com.rh.note.model.component.ModelImpl
import com.rh.note.model.component.RootNodeImpl
import com.rh.note.model.component.TextAreaImpl
import com.rh.note.model.component.TextAreaScrollImpl
import com.rh.note.model.component.TreeImpl
import com.rh.note.model.file.Title
import com.rh.note.model.grammar.Include
import com.rh.note.view.InputWindow

/**
 * 工作窗口服务
 */
class WorkViewAPI {

    /**
     * 打开work_frame
     */
    void openFrame() {
        new WorkFrameFactory().start()
    }

    /**
     * 加载文件标题列表
     */
    void showTitleList(Title rootTitle) {
        RootNodeImpl.create(rootTitle)
        def rootNode = new RootNodeImpl().init()
        new ModelImpl().init()?.setRoot(rootNode)
        new TreeImpl().init()?.expandAllRow()
    }

    /**
     * 展示已打开的编辑区,通过被选择的标题
     */
    String showEditingAreaForExistingSelected() {
        def tree = new TreeImpl().init()
        def title = tree?.getSelectionUserObject()
        def textAreaScroll = new TextAreaScrollImpl().init(title?.getAbsolutePath())
        if (!textAreaScroll) {
            return title?.getAbsolutePath()
        }
        def editArea = new EditAreaImpl().init()
        editArea.show(textAreaScroll.id)
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    void openNewEditingAreaForSelected(String absolutePath, File file) {
        //创建添加编辑区控件
        TextAreaImpl.create(absolutePath)
        def tas = new TextAreaScrollImpl().init(absolutePath)
        def editArea = new EditAreaImpl().init()
        tas.addTo(editArea)
        editArea.showLast()

        //将数据显示到编辑区
        def textArea = new TextAreaImpl().init(absolutePath)
        textArea.read(file)
    }

    /**
     * include语法重命名
     */
    void rename(String componentId) {
        TextAreaImpl textArea = new TextAreaImpl().init(componentId)
        def lineContent = textArea?.getLineContentOfTextArea()
        //获得include语法对象
        Include include = new Include().init(lineContent)
        //弹窗修改为新名字
        def newTitleName = new InputWindow(include?.getTitleName()).getInputValue()
        //在编辑控件中修改为新的语法语句
        textArea?.replaceName(include?.getTitleName(), newTitleName)
    }
}
