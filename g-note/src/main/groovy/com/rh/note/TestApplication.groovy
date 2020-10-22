package com.rh.note

import javax.swing.JFrame

class TestApplication {
    public static void main(String[] args) {
        def frame = new JFrame()
        println JFrame.class.isInstance(frame)
    }
}
