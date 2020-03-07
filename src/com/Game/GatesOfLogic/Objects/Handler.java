package com.Game.GatesOfLogic.Objects;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick(){
		//updates each game objects
		for(int i=0; i<object.size(); i++){
			GameObject obj = object.get(i);
			obj.tick();
		}
	}
	
	public void render(Graphics g){
		//renders each game objects
		for(int i=0; i<object.size(); i++){
			GameObject obj = object.get(i);
			obj.render(g);
		}
	}
	
	//adds/remove method for game objects
	public void addObject(GameObject object){	this.object.add(object);	}
	public void removeObject(GameObject object){	this.object.remove(object);   }
}