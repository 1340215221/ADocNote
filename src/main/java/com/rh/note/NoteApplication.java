package com.rh.note;

import com.rh.note.aspect.DoActionLog;
import com.rh.note.aspect.GlobalExceptionHandler;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.util.aop.ProxyUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 笔记软件启动程序
 */
@Slf4j
public class NoteApplication implements INoteApplication {

    public static void main(String[] args) {
//        log.info("启动笔记软件");
//        FlatDarculaLaf.install();
//        log.info("完成Darcula主题初始化");
//        BeanConfig.projectListAction.startFrame();
//        log.info("完成[笔记APP]初始化");

        INoteApplication bean = (INoteApplication) new ProxyUtil().getBean(NoteApplication.class);
        bean.hello();
        System.out.println("");
    }

    @GlobalExceptionHandler
    @DoActionLog("测试方法")
    @Override
    public void hello() {
        throw new NoteException(ErrorCodeEnum.FILE_READ_FAILED);
    }

}
