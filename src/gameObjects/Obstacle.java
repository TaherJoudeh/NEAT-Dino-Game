package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Animation;
import game.Game;
import game.GameObjectHandler;

public class Obstacle extends GameObject {
		
	public static ArrayList<Obstacle> obs = new ArrayList<Obstacle> ();
	
	public Obstacle(float x, float y, float velX, float velY, int width, int height, Animation animation,
			GameObjectHandler handler) {
		super(x, y, velX, velY, width, height, animation, handler);
	}
	public Obstacle(float x, float y, float velX, float velY, int width, int height, BufferedImage image,
			GameObjectHandler handler) {
		super(x, y, velX, velY, width, height, image, handler);
	}

	@Override
	public void update() {
		
		super.setX(super.getX()+super.getVelX());
		
		if (super.getX()+super.getWidth() <= 0) super.getGameObjectHandler().removeGameObject(this);
		if (super.getX()+super.getWidth() < super.getGameObjectHandler().getPlayer().getX())
			obs.remove(this);
		
	}

	@Override
	public void render(Graphics2D g2d) {
		
		Graphics g = (Graphics) g2d;
		
		g.drawImage(super.getImage(), (int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight(), null);
		
		if (Game.renderingHitBox) {
			g.setColor(Color.RED);
			g.drawRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight());
		}
		
	}

	
	
}
