package com.rh.note.action

import com.rh.note.api.IFileAPIService
import com.rh.note.entity.adoc.AdocTitile
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import javax.swing.JTree
import java.util.stream.Stream

class ProjectListAction implements ISwingBuilder {

    private IFileAPIService fileAPIService

    ProjectListAction(IFileAPIService fileAPIService) {
        this.fileAPIService =fileAPIService
    }

    /**
     * 获得项目文件目录
     */
    void queryProjectList(String path) {
        def list = fileAPIService.queryProjectList(path)

        def node = swingBuilder.node(userObject: this.getProjectName(path)){
            this.buildNode(list)
        }

        swingBuilder.file_list_model.root = node

        def tree = swingBuilder.file_list_tree as JTree
        for (int i = 1; i <= tree.rowCount; i++) {
            tree.expandRow(i)
        }
    }

    /**
     * 构建节点
     */
    private void buildNode(List<AdocTitile> list) {
        if (CollectionUtils.isEmpty(list)) {
            return
        }

        list.each {AdocTitile titile ->
            swingBuilder.node(userObject: titile) {
                this.buildNode(titile.childrenTitle)
            }
        }

    }

    /**
     * 获得项目名字
     */
    private String getProjectName(String path) {
        if (StringUtils.isBlank(path)) {
            return ''
        }

        def split = path.split(this.getFileSeparatorAsRegex())
        if (split == null || split.length < 1) {
            return ''
        }

        def lastStr = split[split.length - 1]
        if (StringUtils.isNotBlank(lastStr)) {
            return lastStr
        }

        if (split.length > 1) {
            return split[split.length - 2]
        }

        return ''
    }

    /**
     * 获得文件分隔符的正则字符串
     */
    private String getFileSeparatorAsRegex() {
        if ('\\' == File.separator) {
            return '\\\\'
        }

        return File.separator
    }

}
