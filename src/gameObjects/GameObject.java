package gameObjects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import game.Animation;
import game.GameObjectHandler;

public abstract class GameObject {

	private float x, y, velX, velY;
	private int width, height;
	private GameObjectHandler handler;
	private Animation animation;
	
	private BufferedImage image;
	
	public GameObject(float x, float y, float velX, float velY, int width, int height, Animation animation, GameObjectHandler handler) {

		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.width = width;
		this.height = height;
		this.animation = animation;
		this.handler = handler;
		
	}
	public GameObject(float x, float y, float velX, float velY, int width, int height, BufferedImage image, GameObjectHandler handler) {

		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.width = width;
		this.height = height;
		this.image = image;
		this.handler = handler;
		
	}

	
	public float getX() { return x; }
	public void setX(float x) { this.x = x; }
	
	public float getY() { return y; }
	public void setY(float y) { this.y = y; }
	
	public float getVelX() { return velX ;}
	public void setVelX(float velX) { this.velX = velX; }
	
	public float getVelY() { return velY; }
	public void setVelY(float velY) { this.velY = velY; }
	
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width; }
	
	public int getHeight() { return height; }
	public void setHeight(int height) { this.height = height; }

	public Animation getAnimation() { return animation; }
	public void setAnimation(Animation animation) { this.animation = animation; }
	
	public BufferedImage getImage() { return image; }
	public void setImage(BufferedImage image) { this.image = image; }
	
	public GameObjectHandler getGameObjectHandler() { return handler; }
	public void setGameObjectHandler(GameObjectHandler goh) { handler = goh; }
	
	public Rectangle2D getBounds() {
		return new Rectangle2D.Float(x,y,width,height);
	}
	
	abstract public void update();
	abstract public void render(Graphics2D g2d);
	
}
