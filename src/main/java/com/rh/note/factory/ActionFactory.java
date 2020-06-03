package com.rh.note.factory;

import com.rh.note.action.EditAction;
import com.rh.note.api.impl.FileAPIServiceImpl;
import com.rh.note.service.impl.AdocServiceImpl;
import com.rh.note.service.impl.FileServiceImpl;
import lombok.Getter;

@Getter
public class ActionFactory {

    private EditAction editAction = new EditAction(
            new FileAPIServiceImpl(
                    new FileServiceImpl(),
                    new AdocServiceImpl()
            )
    );

}
