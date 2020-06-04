package com.rh.note.factory;

import com.rh.note.action.EditAction;
import com.rh.note.action.ProjectListAction;
import com.rh.note.action.ProjectManageAction;
import com.rh.note.api.impl.FileAPIServiceImpl;
import com.rh.note.service.impl.AdocServiceImpl;
import com.rh.note.service.impl.FileServiceImpl;
import lombok.Getter;

@Getter
public class ActionFactory {

    public static final ActionFactory action_factory = new ActionFactory();

    private EditAction editAction = new EditAction(
            new FileAPIServiceImpl(
                    new FileServiceImpl(),
                    new AdocServiceImpl()
            )
    );

    private ProjectManageAction projectManageAction = new ProjectManageAction();

    private ProjectListAction projectListAction = new ProjectListAction();

}
