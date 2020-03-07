package com.Game.GatesOfLogic.Objects.others;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Solve{
	//----------instance variables----------
	private int x, y, width, height;
	private String text = "SOLVE";
	private boolean enabled = true;
	
	//----------constructor----------
	public Solve(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//----------render method----------
	public void render(Graphics g){
		g.setColor(Color.CYAN);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
		g.drawString(text, x+10, y+30);
		g.drawRect(x, y, width, height);
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	//--------getter and setter for text-------
	public String getText(){	return text;	}
	public void setText(String text){	this.text = text;	}
	public boolean isEnabled(){	return enabled;	}
	public void setEnabled(boolean enabled){	this.enabled = enabled;	}
}