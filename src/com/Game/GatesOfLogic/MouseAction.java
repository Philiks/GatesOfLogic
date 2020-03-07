package com.Game.GatesOfLogic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.Game.GatesOfLogic.Objects.GameObject;
import com.Game.GatesOfLogic.Objects.Handler;
import com.Game.GatesOfLogic.Objects.ID;
import com.Game.GatesOfLogic.Objects.others.MissingGate;
import com.Game.GatesOfLogic.Objects.others.NextOrRetry;
import com.Game.GatesOfLogic.Objects.others.Solve;

public class MouseAction extends MouseAdapter {
	// -------------instance variables-------------
	private int dx, dy;
	//gets the evaluation of all missing gate
	//if one or more is false, then the result is false
	private boolean isCorrect;

	// -------------object of class-------------
	private Point p;
	private Handler handler;
	private Solve solve;
	private ArrayList<MissingGate> missingGate;
	private NextOrRetry next_or_retry;
	
	// -------------constructor-------------
	public MouseAction(Handler handler, Solve solve, NextOrRetry next_or_retry, ArrayList<MissingGate> missingGate) {
		p = new Point();
		this.handler = handler;
		this.solve = solve; 
		this.missingGate = missingGate;
		this.next_or_retry = next_or_retry;
	}

	public void mousePressed(MouseEvent me) {
		p.setLocation(me.getX(), me.getY());
		if(solve.getBounds().contains(p) && solve.isEnabled()){
			for(int i=0; i<missingGate.size(); i++){
				//gets all evaluation of each missing gates
				isCorrect = missingGate.get(i).getEvaluation();
				//once a wrong answer has been detected, break the loop
				//to make isCorrect value = false
				if(!isCorrect) break;
			}

			//if isCorrect, change color of all object
			//if object is rendered through color,
			//green = correct, red = wrong
			//if object is rendered through image,
			//green = correct, red = wrong sprite
			//	for game objects
			for(int j=0; j<handler.object.size(); j++){
				GameObject object = handler.object.get(j);

				//lines
				if(object.getId() == ID.LINE){
					if(isCorrect)	object.setColor(Color.GREEN);
					else 			object.setColor(Color.RED);
					continue;
				}else if(object.getId() == ID.OFF || object.getId() == ID.ON)
					continue;
				else{
					//all objects aside from those that are not in container
					if(!object.isMovable()){
						if(isCorrect)	object.setRow(2);	//row 2 has the green image
						else			object.setRow(3);	//row 3 has the red image
					}
					if(object.isInsideContainer()) object.setMovable(false);
				}	
			}
			
			//	for missing gates
			for(int k=0; k<missingGate.size(); k++){
				if(isCorrect)	missingGate.get(k).setColor(Color.GREEN);
				else			missingGate.get(k).setColor(Color.RED);
			}
			
			if(isCorrect){
				next_or_retry.setVisible(true);
				next_or_retry.setCorrect(true);
			}else{
				next_or_retry.setVisible(true);
				next_or_retry.setCorrect(false);
			}
			
			//disables the SOLVE button
			solve.setEnabled(false);
		}
		
		//actions for next_or_retry
		if(next_or_retry.isVisible()){
			if(next_or_retry.getBounds().contains(p)){
				if(isCorrect) System.exit(0);
				else{	//brings everything back to normal
					for(int i=0; i<handler.object.size(); i++){
						GameObject object = handler.object.get(i);

						if(object.getId() == ID.LINE) object.setColor(Color.WHITE);
						else object.setRow(1);

						for(int j=0; j<missingGate.size(); j++){
							//goes back to normal the gates that should be inside container
							missingGate.get(j).setEntry(true);
							missingGate.get(j).leftContainer();
						}
						if(object.isInsideContainer()){
							object.setX(object.getOrigX());
							object.setY(object.getOrigY());
							object.setMovable(true);
							object.setInside(false);
						}

					}
				}
				//makes next_or_retry invisible again
				next_or_retry.setVisible(false);
				//re-enabled SOLVE button
				solve.setEnabled(true);
			}
		}
		//moves the gates in container
		for(int k=0; k<handler.object.size(); k++){
			GameObject object = handler.object.get(k);
			if(object.getBounds().contains(p) && object.isMovable()){
				object.setInside(true);
				dx = me.getX() - object.getX();
				dy = me.getY() - object.getY();
			}
		}
	}

	public void mouseDragged(MouseEvent me) {
		for(int i=0; i<handler.object.size(); i++){
			GameObject object = handler.object.get(i);
			if(object.getBounds().contains(p) && object.isMovable() && object.isInside()){
				p.setLocation(me.getX() - dx, me.getY() - dy);
				object.setX(p.x); 
				object.setY(p.y);
			}
		}
	}

	public void mouseReleased(MouseEvent me) {
		for(int i=0; i<handler.object.size(); i++){
			GameObject object = handler.object.get(i);
			if(object.getBounds().contains(p) && object.isMovable()){
				for(int j=0; j<missingGate.size(); j++){
					//if the gate does not intersect to any missingGate
					//return to container
					if(!object.getBounds().intersects(missingGate.get(j).getBounds())){
						object.setX(object.getOrigX());
						object.setY(object.getOrigY());
						object.setInside(false);
					}
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent me){
		for(int i=0; i<handler.object.size(); i++){
			GameObject object = handler.object.get(i);
			if(object.getBounds().contains(p) && me.getClickCount() == 2){
				for(int j=0; j<missingGate.size(); j++){
					//if the gates is inside a missingGate
					//then double-clicked, return to container
					if(object.getBounds().intersects(missingGate.get(j).getBounds())){
						object.setX(object.getOrigX());
						object.setY(object.getOrigY());
						object.setMovable(true);
						object.setInside(false);
						missingGate.get(j).setEntry(true);
						missingGate.get(j).leftContainer();
						System.out.println(missingGate.get(j).getEvaluation());
					}
				}
			}
		}
	}
}
