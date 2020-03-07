package com.Game.GatesOfLogic.Objects.others;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.GatesOfLogic.Objects.GameObject;
import com.Game.GatesOfLogic.Objects.Handler;
import com.Game.GatesOfLogic.Objects.ID;

public class MissingGate{
	//----------instance variables----------
	//	bounds of this missing gate
	private int x, y, width, height;
	//	receives pair of input to be evaluated
	private boolean[] inputs;
	//	evaluate inputs and sets boolean expression
	//	true - 1     false - 0
	private boolean answer;
	//	true if have evaluated an answer
	private boolean haveAnswer = false;
	//	compares the answer to the required answer
	//	compares each element of the array
	//	[true, true] => only "1" should be the result
	//	[false, false] => only "0" should be the result
	//	[true, false] => both "1" & "0" could be the result
	private boolean[] required;
	//	gate can attach to the outline's x and y
	//	only once. returns true again if gate leaves
	private boolean canEnter = true;
	//	is true if color is externally changed
	private boolean isExtChanged = false;
	//	is true if the gate leaves the container by double-clicking
	private boolean leaveContainer = false;
	
	
	//--------object of class--------
	private Handler handler;
	private Color color;
	
	//----------constructor----------
	public MissingGate(int x, int y, int width, int height, Handler handler,
			boolean[] inputs, boolean[] required) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		this.inputs = inputs;
		this.required = required;
		System.out.println(answer);
	}
	
	public void tick(){
		for(int i=0; i<handler.object.size(); i++){
			GameObject obj = handler.object.get(i);
			if(getBounds().intersects(obj.getBounds())){
				if(canEnter){
					obj.setX(x);	obj.setY(y);
					canEnter=false;
					obj.setInside(false);
					obj.setMovable(false);
				}
				
				//evaluates inputs
				if(obj.getId() == ID.AND){
					answer = inputs[0] && inputs[1];
					haveAnswer = true;
				}
				if(obj.getId() == ID.OR){
					answer = inputs[0] || inputs[1];
					haveAnswer = true;
				}
					
			}
		}
	}
	
	public void render(Graphics g){
		if(!isExtChanged){
			if(canEnter) color = Color.CYAN;
			else color = Color.MAGENTA;
		}
		g.setColor(color);
		g.drawRect(x, y, width, height);
	}
	
	//returns true if the boolean answer matches
	//either of the required boolean element
	//return false if does not have an answer
	//or the evaluation is wrong
	public boolean getEvaluation(){
		//if the gate left the missingGate,
		//evaluation will be false
		if(leaveContainer)	return false;
		return haveAnswer ? (answer==required[0]) ||  (answer==required[1]) : false;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	//sets the leaveContainer after double-clicking
	public void leftContainer(){ leaveContainer = true;	}
	
	//setter for entry of the missingGate
	public void setEntry(boolean canEnter){	this.canEnter = canEnter; isExtChanged = false;	}
	
	//setter for color
	public void setColor(Color color){	this.color = color; isExtChanged = true;	}
}