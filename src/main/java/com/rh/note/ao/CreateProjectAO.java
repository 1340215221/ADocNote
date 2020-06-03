package com.rh.note.ao;

import com.rh.note.constant.ErrorCodeEnum;
import com.rh.note.exception.AdocException;
import lombok.*;

import java.io.File;










/**
 * 创建项目
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProjectAO {

    /**
     * 项目名
     */
    @NonNull
    private String name;

    /**
     * 项目路径
     */
    @NonNull
    private String path;

    private void checkPathValue() {
        if (path.length() < 1) {
            path = File.separator;
        }

        if (File.separatorChar != path.charAt(path.length() - 1)) {
            path += File.separator;
        }
    }

    private void checkNameValue() {
        if (name.length() < 1) {
            throw new AdocException(ErrorCodeEnum.project_name_is_not_blank);
        }
    }

    private void checkFieldValue() {
        this.checkPathValue();
        this.checkNameValue();
    }

    /**
     * 生成绝对路径
     */
    public String generateAbsolutePath() {
        this.checkFieldValue();
        return path + name + File.separator;
    }
}
