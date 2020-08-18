package com.rh.note

import com.rh.note.action.WorkAction
import com.rh.note.util.aop.ProxyUtil

class TestApplication {

    public static void main(String[] args) {
        def util = new ProxyUtil()
        def bean = util.getBean(WorkAction)
        bean.hello()
        println ''
    }

}
