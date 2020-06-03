package com.rh.note.view

import com.rh.note.util.ISwingBuilder

interface SwingBuilderImpl extends ISwingBuilder {

    def init(Closure run)

    String getId()

}