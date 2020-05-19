package com.rh.note.constant;

import lombok.Getter;

@Getter
public enum DirectoryDoctypeEnum {

    book("book"),
    ;

    private String value;

    DirectoryDoctypeEnum(String value) {
        this.value = value;
    }
}
