package com.rh.note.z.demo;

import javax.swing.*;
import java.awt.*;


/**
 * Standalone version of the demo.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public final class RSyntaxTextAreaDemoApp extends JFrame {


	private RSyntaxTextAreaDemoApp() {
		setRootPane(new DemoRootPane());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("RSyntaxTextArea Demo Application");
		pack();
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.
										getSystemLookAndFeelClassName());
//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (Exception e) {
				e.printStackTrace(); // Never happens
			}
			Toolkit.getDefaultToolkit().setDynamicLayout(true);
			new RSyntaxTextAreaDemoApp().setVisible(true);
		});
	}


}
