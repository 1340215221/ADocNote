package com.rh.note;

import javax.swing.ActionMap;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.TextAction;
import java.awt.event.ActionEvent;

public class Test2 {

    public static void add(ActionMap actionMap) {
        actionMap.put(DefaultEditorKit.defaultKeyTypedAction, new TextAction(DefaultEditorKit.defaultKeyTypedAction) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("actionPerformed");
            }
        });
    }

}
