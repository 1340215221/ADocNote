package com.rh.note.view

import com.rh.note.model.MainViewBoundEnum

class EditFileContent implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        def textArea = {
            swingBuilder.textArea(id: "${id}_text",
                    bounds: MainViewBoundEnum.edit_file_content.bound,
            ) {
                run()
            }
        }

        swingBuilder.scrollPane(id: "${id}_scroll",
                bounds: MainViewBoundEnum.edit_file_content.bound,
        ) {
            textArea()
        }
    }

    @Override
    String getId() {
        return 'edit_file_content'
    }
}
