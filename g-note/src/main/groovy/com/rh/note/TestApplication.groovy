package com.rh.note

import groovy.swing.SwingBuilder

import javax.swing.JFrame
import javax.swing.JTextPane
import javax.swing.text.Caret
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.BorderLayout
import java.awt.Color
import java.awt.event.KeyEvent

class TestApplication {
    public static void main(String[] args) {
        def builder = new SwingBuilder()
        builder.frame(id: 'frame',
                size: [900, 500],
                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
        ){
            builder.panel(layout: new BorderLayout()){
                builder.textPane(id: 'textPane',
                        editable: false,
//                        text: 'afdafa\nafdasf\nafasfsa',
                        keyPressed: {KeyEvent event ->
                            println event.keyCode
                            println event.modifiers
                            // ctrl 1 -----49 2
                            // ctrl 2 -----50 2
                        }
                ){
                }
            }
        }
        builder.frame.visible = true

        def textPane = builder.textPane as JTextPane
        def rootElement = textPane.getDocument().getDefaultRootElement()
        def element = rootElement.getElement(0)

        SimpleAttributeSet aSet = new SimpleAttributeSet();
        StyleConstants.setBackground(aSet, Color.green);
        StyledDocument doc = textPane.getStyledDocument();

        doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);

        def caret = textPane.getCaret()
        caret.setDot(0)
        caret.setVisible(true)
    }
}
