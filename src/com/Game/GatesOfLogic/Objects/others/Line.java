package com.Game.GatesOfLogic.Objects.others;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.GatesOfLogic.Objects.GameObject;
import com.Game.GatesOfLogic.Objects.ID;
import com.Game.GatesOfLogic.msc.SpriteSheet;

public class Line extends GameObject{
	//----------instance variables----------
	private int width, height;
	
	public Line(int x, int y, ID id, SpriteSheet ss, int width, int height) {
		super(x, y, id, ss);
		this.width = width;
		this.height = height;
	}	
	
	public void render(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void tick() {		
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}