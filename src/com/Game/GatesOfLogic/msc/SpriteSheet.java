package com.Game.GatesOfLogic.msc;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	BufferedImage img;
	
	public SpriteSheet(BufferedImage img){
		this.img = img;
	}
	
	public BufferedImage getSprite(int col, int row, int width, int height){
		return img.getSubimage((col*width)-width, (row*height)-height, width, height);
	}
}