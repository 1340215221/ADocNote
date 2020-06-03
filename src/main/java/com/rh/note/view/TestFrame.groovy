package com.rh.note.view

import groovy.swing.SwingBuilder

import javax.swing.WindowConstants
import java.awt.Color

class TestFrame {

    public static void main(String[] args) {
        def swingBuilder = new SwingBuilder()

        def panel5 = {
            swingBuilder.panel(id: 'p5',
                    background: Color.pink
            )
        }

        def panel4 = {
            swingBuilder.panel(id: 'p4',
                    background: Color.black
            )
        }

        def panel3 = {
            swingBuilder.panel(id: 'p3',
                    background: Color.yellow
            )
        }

        def panel2 = {
            swingBuilder.panel(id: 'p2',
                    background: Color.green
            )
        }

        def panel1 = {
            swingBuilder.panel(id: 'p1',
                    background: Color.blue
            )
        }

        def layout = {
            swingBuilder.borderLayout(center: panel1(),
                    north: panel2(),
                    south: panel3(),
                    west: null,
                    east: panel5()
            )
        }

        def panel = {
            swingBuilder.panel(background: Color.red){
                layout()
            }
        }

        swingBuilder.frame(id: 'frame',
                bounds: [0, 0, 500, 500],
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ){
            panel()
        }

        swingBuilder.frame.visible = true
    }

}
