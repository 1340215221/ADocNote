package com.rh.note.view.frame

import com.rh.note.constant.SwingKeyEnum
import com.rh.note.utils.Builder
import com.rh.note.view.bound.BoundFactory
import com.rh.note.view.vo.EditAreaVO
import com.rh.note.view.vo.FileListVO
import com.rh.note.view.vo.OpenFileLabelVO

/**
 * 中间区域
 * - 文件列表
 * - 编辑区域
 * - 打开文件的标签
 */
class CenterAreaVO implements Builder {

    private FileListVO fileListVO = new FileListVO()
    private EditAreaVO editAreaVO = new EditAreaVO()
    private OpenFileLabelVO openFileLabelVO = new OpenFileLabelVO()

    @Override
    void init() {
        def fileList = {
            fileListVO.init()
            SwingKeyEnum.FILE_LIST.get()
        }

        def editArea = {
            editAreaVO.init()
            SwingKeyEnum.EDIT_AREA.get()
        }

        def openFileLabel = {
            openFileLabelVO.init()
            SwingKeyEnum.OPEN_FILE_LABEL.get()
        }

        swingBuilder.panel(bounds: BoundFactory.getCenterAreaBound()) {
            fileList()
            editArea()
            openFileLabel()
        }
    }

    @Override
    String getId() {
        return 'centerArea'
    }
}
