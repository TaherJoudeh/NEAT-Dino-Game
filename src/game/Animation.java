package game;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] images;
	private int speed;
	
	private int count;
	private int index = 0;
	
	public Animation(BufferedImage[] images, int speed) {
		this.images = images;
		this.speed = speed;
	}
	
	public void startAnimation() {
		
		if (count == speed) {
			
			if (index == images.length-1) index = 0;
			else index++;
			
			count = 0;
		}else count++;
		
	}
	
	public int getSpeed() { return speed; }
	public void setSpeed(int speed) {
		if (speed <= 0) return;
		this.speed = speed;
	}
	
	public BufferedImage getCurrentImage() { return images[index]; }
	
}
