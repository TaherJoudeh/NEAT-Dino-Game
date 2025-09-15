package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import game.Animation;
import game.Game;
import game.GameObjectHandler;
import game.Images;

public class Bird extends Obstacle {
	
	private Animation birdAnimation;
	
	public Bird(float x, float y, float velX, float velY, int width, int height, Animation animation,
			GameObjectHandler handler) {
		super(x, y, velX, velY, width, height, animation, handler);
		birdAnimation = new Animation(new BufferedImage[] {Images.images.get("bird1"),Images.images.get("bird2")},50);
		super.setAnimation(birdAnimation);
	}

	@Override
	public void update() {
		
		super.setX(super.getX()+super.getVelX());
		super.getAnimation().startAnimation();
		
		if (super.getX()+super.getWidth() <= 0) super.getGameObjectHandler().removeGameObject(this);
		if (super.getX()+super.getWidth() < super.getGameObjectHandler().getPlayer().getX())
			Obstacle.obs.remove(this);
		
	}

	@Override
	public void render(Graphics2D g2d) {
		Graphics g = (Graphics) g2d;
		
		g.drawImage(super.getAnimation().getCurrentImage(), (int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight(), null);
		
		if (Game.renderingHitBox) {
			g.setColor(Color.RED);
			g.drawRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight());
		}
	}
	
}
