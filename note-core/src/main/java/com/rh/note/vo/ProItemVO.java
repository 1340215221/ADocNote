package com.rh.note.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 项目 结果
 */
@Data
@Accessors(chain = true)
public class ProItemVO {
    /**
     * 项目名
     */
    private String projectName;
    /**
     * 项目路径
     */
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
