package com.rh.note.view

import com.rh.note.model.BoundEnum

class EditFileContent implements SwingBuilderImpl {
    @Override
    init(Closure run) {
        def textArea = {
            swingBuilder.textArea(id: "${id}_text",
                    bounds: BoundEnum.edit_file_content.bound,
            ) {
                run()
            }
        }

        swingBuilder.scrollPane(id: "${id}_scroll",
                bounds: BoundEnum.edit_file_content.bound,
        ) {
            textArea()
        }
    }

    @Override
    String getId() {
        return 'edit_file_content'
    }
}
