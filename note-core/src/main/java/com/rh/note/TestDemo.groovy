package com.rh.note

import com.formdev.flatlaf.FlatIntelliJLaf
import com.rh.note.util.GitUtil
import groovy.swing.SwingBuilder

class TestDemo {

    static {
        FlatIntelliJLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {
        pull()
//        reset()
    }

    static void reset() {
        def git = new GitUtil('D:\\my_code\\test-jgit')
        try {
            git.init()
            git.reset()
        } finally {
            git.close()
        }
    }

    static void pull() {
        def git = new GitUtil('D:\\my_code\\test-jgit')
        try {
            git.init()
            git.add()
            git.commit()
            git.pull()
        } finally {
            git.close()
        }
    }
}
