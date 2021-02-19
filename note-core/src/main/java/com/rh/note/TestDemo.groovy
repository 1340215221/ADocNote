package com.rh.note

import com.formdev.flatlaf.FlatIntelliJLaf
import groovy.swing.SwingBuilder

class TestDemo {

    static {
        FlatIntelliJLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {
    }
}
