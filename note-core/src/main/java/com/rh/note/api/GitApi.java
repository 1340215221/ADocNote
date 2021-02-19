package com.rh.note.api;

import com.rh.note.exception.ApplicationException;
import com.rh.note.view.GitView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * git 接口
 */
@Slf4j
@Component
public class GitApi {

    /**
     * 更新代码
     */
    public void pull() throws ApplicationException {
        GitView git = new GitView().init();
        git.pull();
    }
}
