package com.Game.GatesOfLogic.Objects.Gates;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.GatesOfLogic.Objects.GameObject;
import com.Game.GatesOfLogic.Objects.ID;
import com.Game.GatesOfLogic.msc.SpriteSheet;

public class ORGate extends GameObject{
	//-----------constructor-------------
	public ORGate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		col = 1;
		ss.getSprite(col, row, 100, 100);
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(ss.getSprite(col, row, 100, 100), x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 100, 100);
	}
}