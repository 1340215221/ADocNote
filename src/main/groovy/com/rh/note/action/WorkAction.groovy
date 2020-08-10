package com.rh.note.action

import com.rh.note.api.FileAPIService
import com.rh.note.build.LoaderBuild
import com.rh.note.model.component.EditAreaImpl
import com.rh.note.model.component.ModelImpl
import com.rh.note.model.component.RootNodeImpl
import com.rh.note.model.component.TextAreaImpl
import com.rh.note.model.component.TextAreaScrollImpl
import com.rh.note.model.component.TreeImpl
import com.rh.note.model.file.Title
import com.rh.note.model.grammar.Include
import com.rh.note.util.ISwingBuilder
import com.rh.note.view.InputWindow
import com.rh.note.view.RootNode
import org.apache.commons.collections4.CollectionUtils

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
class WorkAction implements ISwingBuilder, LoaderBuild {

    FileAPIService fileAPIService

    /**
     * 显示或隐藏标题列表
     */
    static def hiddenOrShowTitleList = {
        workLoader.hiddenOrShowTitleList()
    }

    /**
     * 构建节点
     */
    private void buildNode(List<Title> list) {
        if (CollectionUtils.isEmpty(list)) {
            return
        }

        list.each { Title titile ->
            swingBuilder.node(userObject: titile) {
                this.buildNode(titile.childrenTitle)
            }
        }

    }

    /**
     * 加载文件标题列表
     */
    void showTitleList() {
        Title rootTitle = fileAPIService.findAllTitle()
        workLoader.setTitleTree(rootTitle)
        if (rootTitle != null) {
            new RootNode(rootTitle).init {}
            def rootNode = new RootNodeImpl().init()
            new ModelImpl().init().setRoot(rootNode)
        }
        workLoader.expandAllNode()
    }

    /**
     * 打开选择标题
     */
    void openAdocFile() {
        def tree = new TreeImpl().init()
        def title = tree?.getSelectionUserObject()
        def textAreaScroll = new TextAreaScrollImpl().init(title?.getAbsolutePath())
        if (textAreaScroll) {
            def editArea = new EditAreaImpl().init()
            textAreaScroll.show(editArea)
            return
        }
        //读取文件内容
        def file = fileAPIService.readTitleFileContent(title.getAbsolutePath())

        //创建添加编辑区控件
        TextAreaImpl.create(title?.getAbsolutePath())
        def tas = new TextAreaScrollImpl().init(title?.getAbsolutePath())
        def editArea = new EditAreaImpl().init()
        tas.addTo(editArea)
        editArea.showLast()

        //将数据显示到编辑区
        def textArea = new TextAreaImpl().init(title?.getAbsolutePath())
        textArea.read(file)
    }

    /**
     * 修改include文件名字
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
