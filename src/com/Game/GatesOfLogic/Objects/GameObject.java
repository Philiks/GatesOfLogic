package com.Game.GatesOfLogic.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.GatesOfLogic.msc.SpriteSheet;

public abstract class GameObject {
	//--------instance variable--------
	protected int x, y;
	protected ID id;
	protected boolean inside = false, move = false, insideContainer = false;
	//-----for gates inside container only-------
	//	stores the original position of the gate
	//	to be placed again when wanted
	//	to go outside the missing gate
	protected int origX, origY;
	//for line object
	protected Color color = Color.WHITE;
	//for SpriteSheet row and column
	//all default gates and inputs are in first row
	protected int row = 1, col;
	
	//----------object of class------
	protected SpriteSheet ss;
	
	//---------constructor---------
	public GameObject(int x, int y, ID id, SpriteSheet ss){
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
		origX = x;
		origY = y;
	}

	//---------abstract methods--------
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	//-------------getter and setter-------------
	public int getX() {	return x;	}
	public void setX(int x) { this.x = x;	}
	public int getY() {	return y;	}
	public void setY(int y) { this.y = y;	}
	public ID getId() {	return id;	}
	public void setId(ID id) {	this.id = id;	}
	public void setInside(boolean inside){	this.inside = inside;	}
	public boolean isInside(){	return inside;	}
	public void setMovable(boolean move){	this.move = move;	}
	public boolean isMovable(){	return move;	}
	public void setInsideContainer(boolean insideContainer){	this.insideContainer = insideContainer;	}
	public boolean isInsideContainer(){	return insideContainer;	}
	//setter of color for line objects
	public void setColor(Color color){	this.color = color;	}
	//setter of sprite row  
	//for gates and inputs objects
	//columns for each gates and inputs remains constant
	//except for the row
	public void setRow(int row){	this.row = row;	}
	
	
	//-------getter for origX and origY
	public int getOrigX() {	return origX;	}
	public int getOrigY() {	return origY;	}
}