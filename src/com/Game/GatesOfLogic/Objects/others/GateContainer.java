package com.Game.GatesOfLogic.Objects.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GateContainer{
	//----------instance variables----------
	private int x, y, width, height;
	
	//----------constructor----------
	public GateContainer(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//----------render method----------
	public void render(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect(x, y, width+15, height+15);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x+5, y+5, width+5, height+5);
	}
	
	//---------get bounds method----------
	public Rectangle getBounds(){
		return new Rectangle(x, y, 100, 100);
	}
}