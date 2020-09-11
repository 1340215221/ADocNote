package com.rh.note.vo;

import lombok.Data;

@Data
public class RecentlyOpenedRecordVO {

    private String projectName;
    private String projectPath;

    public String toString(){
        return "<html>" +
                "<body>" +
                "<p><b><i>" + projectName + ":</i></b></p>" +
                "<p>" + projectPath + "</p>" +
                "</body>" +
                "</html>";
    }

}
