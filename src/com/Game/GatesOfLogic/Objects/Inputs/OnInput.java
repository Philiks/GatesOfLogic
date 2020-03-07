package com.Game.GatesOfLogic.Objects.Inputs;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.Game.GatesOfLogic.Objects.GameObject;
import com.Game.GatesOfLogic.Objects.ID;
import com.Game.GatesOfLogic.msc.SpriteSheet;

public class OnInput extends GameObject{
	//-----------constructor-------------
	public OnInput(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		col = 2;
		ss.getSprite(col, row, 20, 20);
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.drawImage(ss.getSprite(col, row, 20, 20), x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 20, 20);
	}
}