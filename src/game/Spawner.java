package game;

import java.awt.image.BufferedImage;
import java.util.Random;

import gameObjects.Bird;
import gameObjects.Obstacle;

public class Spawner {

	private float velX;
	private int delay;
	
	private Random R = new Random();
	
	private int count;
	
	private GameObjectHandler handler;
		
	public Spawner(float velX, int delay, GameObjectHandler handler) {
		this.velX = velX;
		this.delay = delay;
		
		this.handler = handler;
	}
	
	public float getVelX() { return velX; }
	public void setVelX(float velX) {
		if (velX == -5) return;
		count = 0;
		this.velX = velX; 
	}
	
	public int getDelay() { return delay; }
	public void setDelay(int delay) {
		if (delay == 100) return;
		count = 0;
		this.delay = delay; 
	}
	
	public void spawn() {
		
		if (count == delay) {
		
			if (R.nextInt(10) >= 6 && Game.score > 10) {
				
				int y = 0;
				
				switch (R.nextInt(3)) {
				case 0: y = 270; break;
				case 1: y = 310; break;
				default: y = 230; break;
				}
				
				Bird bird = new Bird(Window.WIDTH,y,velX,0,42,30,null,handler);
				handler.addGameObject(bird);
				Obstacle.obs.add(bird);
				
				count = 0;
				return;
			}
			
			BufferedImage image = null;
			boolean isLarge = R.nextBoolean();
			int width = 0, height = 0;
		
			if (isLarge)
				height = 48;
			else height = 33;
		
			switch (R.nextInt(3)) {
			case 0:
				if (isLarge) {
					image = Images.images.get("large1");
					width = 23;
				}else {
					image = Images.images.get("small1");
					width = 15;
				}
				break;
			case 1:
				if (isLarge) {
					image = Images.images.get("large2");
					width = 48;
				}else {
					image = Images.images.get("small2");
					width = 32;
				}
				break;
			default:
				if (isLarge) {
					image = Images.images.get("large3");
					width = 73;
				}else {
					image = Images.images.get("small3");
					width = 49;
				}
				break;
			}
			Obstacle obstacle = new Obstacle(Window.WIDTH,337-height,velX,0,width,height,image,handler);
			handler.addGameObject(obstacle);
			Obstacle.obs.add(obstacle);
			count = 0;
		}
	
		count++;
		
	}
	
	public void reset() {
		
		velX = -1.5f;
		count = 0;
		
	}
	
}
