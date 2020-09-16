package com.rh.note


import javax.swing.JTextPane
import javax.swing.event.CaretEvent
import javax.swing.event.CaretListener

class TestApplication {
    public static void main(String[] args) {
        def pane = new JTextPane()
        pane.addCaretListener(new CaretListener() {
            @Override
            void caretUpdate(CaretEvent e) {

            }
        })
    }
}
