package com.rh.note.constant;

import lombok.Getter;

@Getter
public enum DirectoryTopEnum {

    left("top"),
    ;
    private String value;
    DirectoryTopEnum(String value) {
        this.value = value;
    }

}
