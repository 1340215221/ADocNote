package com.rh.note.action

import com.rh.note.entity.adoc.AdocTitile
import com.rh.note.util.ISwingBuilder

class ProjectListAction implements ISwingBuilder {

    /**
     * 获得项目文件目录
     */
    List<AdocTitile> queryProjectList(String path) {
        [
                new AdocTitile(displayName: 'README.adoc'),
                new AdocTitile(displayName: 'twoLevel',
                        childrenTitle: [
                                new AdocTitile(displayName: 'Java基础'),
                                new AdocTitile(displayName: '数据结构与算法'),
                        ]
                ),
                new AdocTitile(displayName: 'content')
        ]
    }

}
