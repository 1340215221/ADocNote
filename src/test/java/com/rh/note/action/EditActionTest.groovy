package com.rh.note.action

import com.rh.note.ao.CreateProjectAO
import com.rh.note.api.impl.FileAPIServiceImpl
import com.rh.note.service.impl.AdocServiceImpl
import com.rh.note.service.impl.FileServiceImpl

class EditActionTest extends GroovyTestCase {

    private EditAction editAction = new EditAction(
            new FileAPIServiceImpl(
                    new FileServiceImpl(),
                    new AdocServiceImpl()
            )
    )

    void test_createProject() {
        def ao = new CreateProjectAO(name: 'myadocPro',
                path: 'D:\\test'
        )
        def project = editAction.createProject(ao)
        println project.path
    }

}
