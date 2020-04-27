package com.rh.note.exception;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
public class NoteException extends RuntimeException {

    public NoteException(String msg) {
        super(msg);
    }

}
