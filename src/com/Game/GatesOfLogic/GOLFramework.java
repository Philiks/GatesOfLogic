package com.Game.GatesOfLogic;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.Game.GatesOfLogic.Objects.Handler;
import com.Game.GatesOfLogic.Objects.ID;
import com.Game.GatesOfLogic.Objects.Gates.ANDGate;
import com.Game.GatesOfLogic.Objects.Gates.NOTGate;
import com.Game.GatesOfLogic.Objects.Gates.ORGate;
import com.Game.GatesOfLogic.Objects.Inputs.OffInput;
import com.Game.GatesOfLogic.Objects.Inputs.OnInput;
import com.Game.GatesOfLogic.Objects.others.GateContainer;
import com.Game.GatesOfLogic.Objects.others.Line;
import com.Game.GatesOfLogic.Objects.others.MissingGate;
import com.Game.GatesOfLogic.Objects.others.NextOrRetry;
import com.Game.GatesOfLogic.Objects.others.Solve;
import com.Game.GatesOfLogic.msc.BufferedImageLoader;
import com.Game.GatesOfLogic.msc.SpriteSheet;

public class GOLFramework extends Canvas implements Runnable {
	// serial ID
	private static final long serialVersionUID = 1L;

	//---------arrayList---------
	private ArrayList<MissingGate> missingGate = new ArrayList<MissingGate>();
	
	//-----------array-----------
	//	stores two inputs then make it null
	//	after passing to the missingGate
	private boolean[] inputs = new boolean[2];
	//	stores two required answers then make it null
	//	after passing to the missingGate
	private boolean[] required = new boolean[2];
	
	//----------constant variables-----------
	//	width and height with 3x4 resolution
	public final static int HEIGHT = 690, WIDTH = 920;

	//------instance variables------
	private boolean isRunning = false;
	
	//------objects of class------
	Thread thread;
	Handler handler;
	BufferedImage level_img = null;
	BufferedImage sprite_sheet_gates = null;
	BufferedImage sprite_sheet_inputs = null;
	MouseAction mouseAction;
	SpriteSheet ssg; 	//for gates
	SpriteSheet ssi; 	//for inputs
	GateContainer gc;	//container for movable gates
	Solve solve;		//solve button
	NextOrRetry next_or_retry;
	
	//---------constructor------------
	public GOLFramework() {
		new Window(WIDTH, HEIGHT, "Gates of Logic", this);
		start();
		handler = new Handler();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level_img = loader.loadImage("/level/gol_level_two.png");
		sprite_sheet_gates = loader.loadImage("/sprite_sheet_gates.png");
		sprite_sheet_inputs = loader.loadImage("/sprite_sheet_inputs.png");
		
		ssg = new SpriteSheet(sprite_sheet_gates);
		ssi = new SpriteSheet(sprite_sheet_inputs);
		drawLevel(level_img);
		
		mouseAction = new MouseAction(handler, solve, next_or_retry, missingGate);
		this.addMouseListener(mouseAction);
		this.addMouseMotionListener(mouseAction);
	}

	private void tick() {
		handler.tick();
		
		for(int i=0; i<missingGate.size(); i++){
			MissingGate mg = missingGate.get(i);
			mg.tick();
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		///////////////////////////////////
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for(int h=0; h<missingGate.size(); h++){
			MissingGate mg = missingGate.get(h);
			mg.render(g);
		}
		
		if(gc != null)	gc.render(g);
		
		if(solve != null)	solve.render(g);
		
		if(next_or_retry != null) next_or_retry.render(g);
		
		handler.render(g);
		
		///////////////////////////////////
		g.dispose();
		bs.show();
	}

	//draws the given level
	private void drawLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();
		
		//scans every y of each x 
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int pixel = level.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = pixel & 0xff;
				
				//inputs
				//	ON - 1
				if(red==255 && green==255 && blue==0)
					handler.addObject(new OnInput(xx*20, yy*20, ID.ON, ssi));
				//	OFF - 0
				if(red==0 && green==255 && blue==255)
					handler.addObject(new OffInput(xx*20, yy*20, ID.OFF, ssi));
				
				//gates
				//	OR gate
				if(red==255 && green==0 && blue==0)
					handler.addObject(new ORGate(xx*20, yy*20, ID.OR, ssg));
				//	AND gate
				if(red==0 && green==255 && blue==0)
					handler.addObject(new ANDGate(xx*20, yy*20, ID.AND, ssg));
				//	NOT gate
				if(red==0 && green==0 && blue==255)
					handler.addObject(new NOTGate(xx*20, yy*20, ID.NOT, ssg));
				
				//lines
				//	horizontal lines
				if(red==64 && green==64 && blue==64)
					handler.addObject(new Line(xx*20, yy*20+7, ID.LINE, null, 20, 3));
				//	vertical lines
				if(red==96 && green==96 && blue==96)
					handler.addObject(new Line(xx*20+17, yy*20-13, ID.LINE, null, 3, 43));
				//stores input into the array
				//	[true, true] - ON,ON
				if(red==64 && green==64 && blue==0){ 
					inputs[0] = true;	
					inputs[1] = true;	
				}
				//	[false, false] - OFF,OFF
				if(red==0 && green==64 && blue==64){ 
					inputs[0] = false;	
					inputs[1] = false;	
				}
				//	[true, false] - ON,OFF or OFF,ON
				if(red==64 && green==0 && blue==64){ 
					inputs[0] = true;	
					inputs[1] = false;	
				}
				
				//stores required answer into the array
				//	[true, true] - ON
				if(red==128 && green==128 && blue==0){ 
					required[0] = true;	
					required[1] = true;	
				}
				//	[false, false] - OFF
				if(red==0 && green==128 && blue==128){ 
					required[0] = false;	
					required[1] = false;	
				}
				//	[true, false] - ON,OFF or OFF,ON
				if(red==128 && green==128 && blue==128){ 
					required[0] = true;	
					required[1] = false;	
				}
				
				//missing gate outline
				if(red==128 && green==0 && blue==128){
					MissingGate mg = new MissingGate(xx*20, yy*20, 100, 100, handler, inputs, required);
					missingGate.add(mg);
				}	
				
				//creating an instance of the solve button
				if(red==64 && green==128 && blue==255)
					solve = new Solve(xx*20, yy*20, 100, 40);
				
				//creating an instance of the NextOrRetry button
				if(red==32 && green==64 && blue==128)
					next_or_retry = new NextOrRetry(xx*20, yy*20, 240, 100);
				
				//setting coordinates for gate container
				if(red==192 && green==192 && blue==192)
					gc = new GateContainer(xx*20, yy*20, 500, 140);
				
				//gates inside the container
				//	OR Gate
				if(red==128 && green==0 && blue==0){
					ORGate or = new ORGate(xx*20, yy*20, ID.OR, ssg);
					or.setMovable(true);
					or.setInsideContainer(true);
					handler.addObject(or);
				}
				//	AND Gate
				if(red==0 && green==128 && blue==0){
					ANDGate and = new ANDGate(xx*20, yy*20, ID.AND, ssg);
					and.setMovable(true);
					and.setInsideContainer(true);
					handler.addObject(and);
				}
				
				//proceed to next x 
				if(red==150 && green==100 && blue==50)
					break;
				
				//marks the end of the scan
				if(red==255 && green==0 && blue==255)
					return;
			}
		}
	}

	// ------game loop methods-----
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			isRunning = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (isRunning)
				render();
		}
		stop();
	}

	public static void main(String[] args) {
		new GOLFramework();
	}
}