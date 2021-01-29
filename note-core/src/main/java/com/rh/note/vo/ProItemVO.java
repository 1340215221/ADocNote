package com.rh.note.vo;

import com.rh.note.config.UserProPathConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * 是空的
     */
    public boolean isNotEmpty() {
        return StringUtils.isNotBlank(projectName) && StringUtils.isNotBlank(projectPath);
    }

    public String toString(){
        return "<html>" +
                "<body>" +
                "<p><b><i>" + projectName + ":</i></b></p>" +
                "<p>" + projectPath + "</p>" +
                "</body>" +
                "</html>";
    }

    public void copy(UserProPathConfig.ProItem item) {
        if (item == null) {
            return;
        }
        projectName = item.getProName();
        projectPath = item.getProPath();
    }
}
