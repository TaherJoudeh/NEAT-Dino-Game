package gameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import game.GameObjectHandler;
import game.Images;
import game.Window;

public class Ground extends GameObject {

	private Ground ground;
	private Random R = new Random();
	
	public Ground(float x, float y, float velX, float velY, int width, int height, BufferedImage image,
			GameObjectHandler handler) {
		super(x, y, velX, velY, width, height, image, handler);
		
	}

	@Override
	public void update() {
		if (ground == null)
			ground = new Ground(Window.WIDTH,330,super.getVelX(),0,Window.WIDTH,super.getHeight(),Images.images.get("ground2"),super.getGameObjectHandler());
		
		ground.setVelX(super.getVelX());
		if (ground.getX() == Window.WIDTH)
		switch (R.nextInt(3)) {
			case 0: 
				ground.setImage(Images.images.get("ground1"));
				ground.setY(330);
				break;
			case 1: 
				ground.setImage(Images.images.get("ground2"));
				ground.setY(326);
				break;
			default: 
				ground.setImage(Images.images.get("ground3"));
				ground.setY(326);
				break;
		}
		
		if (super.getX() == Window.WIDTH)
			switch (R.nextInt(3)) {
				case 0:
					super.setImage(Images.images.get("ground1"));
					super.setY(330);
					break;
				case 1: 
					super.setImage(Images.images.get("ground2"));
					super.setY(326);
					break;
				default: 
					super.setImage(Images.images.get("ground3"));
					super.setY(326);
					break;
			}
				
		super.setX(super.getX()+super.getVelX());
		ground.setX(ground.getX()+ground.getVelX());
		
		if ((int)super.getX()+(int)super.getWidth() <= 0) super.setX(Window.WIDTH);
		if ((int)ground.getX()+(int)ground.getWidth() <= 0) ground.setX(Window.WIDTH);
		
	}
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(super.getImage(),(int)super.getX(),(int)super.getY(),super.getWidth(),super.getHeight(),null);
		g2d.drawImage(ground.getImage(),(int)ground.getX(),(int)ground.getY(),ground.getWidth(),ground.getHeight(),null);
	}

	
	
}