package com.Game.GatesOfLogic.Objects.others;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class NextOrRetry {
	//----------instance variables----------
	private int x, y, width, height;
	private String resultText;
	private String actionText;
	private boolean visible = false, isCorrect = false;

	//----------constructor----------
	public NextOrRetry(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	//----------render method----------
	public void render(Graphics g){
		if(isCorrect) {
			resultText = "CONGRATS!";
			actionText = "NEXT";
		}else{
			resultText = "OOPSIE!";
			actionText = "RETRY?";
		}
	
		if(visible){
			g.setColor(Color.CYAN);
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
			g.drawString(resultText, x+10, y+30);
			g.drawLine(x, y+height, x+width, y);
			g.drawString(actionText, x+140, y+80);
			g.drawRect(x, y, width, height);
		}
	}

	public Rectangle getBounds(){
		return visible ? new Rectangle(x, y, width, height) : null;
	}

	//--------getter and setter for text-------
	public void setCorrect(boolean isCorrect){	this.isCorrect = isCorrect;	}
	public boolean isVisible(){	return visible;	}
	public void setVisible(boolean visible){	this.visible = visible;	}
}