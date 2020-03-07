package com.Game.GatesOfLogic;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class ScoreScreen {
	public ScoreScreen(Window window){
		int width = 500, height = 300;
		JDialog screen = new JDialog(window, true);
		screen.setPreferredSize(new Dimension(width, height));
		screen.setMaximumSize(new Dimension(width, height));
		screen.setMinimumSize(new Dimension(width, height));
		
		screen.setResizable(false);
		screen.setLocationRelativeTo(null);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
	}
}