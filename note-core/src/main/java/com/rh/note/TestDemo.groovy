package com.rh.note

import com.rh.note.style.MethodNameLineStyle
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Pattern

class TestDemo {

    static Logger log = LoggerFactory.getLogger(TestDemo)

    static main(args) {
        String str = "    public static void main(args) {";
        def matcher = Pattern.compile(MethodNameLineStyle.regex).matcher(str)
        if (!matcher.find()) {
            println 'not find'
            return
        }
        println matcher.group(1)
    }
}
