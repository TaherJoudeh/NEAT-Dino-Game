package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;
import game.Animation;
import game.Game;
import game.GameObjectHandler;
import game.Images;
import main.java.neat.core.Agent;

public class Player extends GameObject {
	
	public final static Stack<Player> deadPlayers = new Stack<> ();
	
	public final static int PLAYER_RUN = 0,
			PLAYER_DUCK = 1, PLAYER_JUMP_DOWN = 2;
	
	private Animation dinoRunAnimation, dinoDuckAnimation;
	
	public int current_state;
	
	private double score;
	private Agent agent;
	private boolean isDead;
	
	public Player(float x, float y, float velX, float velY, int width, int height, Animation animation,
			GameObjectHandler handler) {
		super(x, y, velX, velY, width, height, animation, handler);
		super.setY(335-super.getHeight());
		dinoRunAnimation = new Animation(new BufferedImage[] { 
				Images.images.get("run1"),Images.images.get("run2"),Images.images.get("run3"),Images.images.get("run4") 
				}, 40);
		dinoDuckAnimation = new Animation(new BufferedImage[] { Images.images.get("duck1"),Images.images.get("duck2") }, 40);
	}
	
	@Override
	public void update() {
		
		super.setY(super.getY()+super.getVelY());
		
		if (!isGrounded()) {
			
			if (current_state == PLAYER_JUMP_DOWN)
				super.setVelY(3);
			else super.setVelY(super.getVelY()+Game.GRAVITY);
			super.setImage(Images.images.get("run1"));
			current_state = PLAYER_RUN;
			
		} else {
			
			if (current_state == PLAYER_RUN) {
				resetWH();
				super.setAnimation(dinoRunAnimation);
			} else if (current_state == PLAYER_DUCK)
				duck();
			super.getAnimation().startAnimation();
			super.setY(335-super.getHeight());
			super.setVelY(0);
		}
		
	}

	public void smallJump() {
		if (!isGrounded()) return;		
		super.setVelY(-3f);
		resetWH();
		super.setY(335-super.getHeight());
	}
	public void bigJump() {
		if (!isGrounded()) return;
		super.setVelY(-3.8f);
		resetWH();
		super.setY(335-super.getHeight());
	}
	public void duck() {
		super.setWidth(55);
		super.setHeight(26);
		super.setAnimation(dinoDuckAnimation);
	}
	
	private void resetWH() {
		super.setWidth(40);
		super.setHeight(43);
	}
	
	public boolean isGrounded() {
		return super.getY()+super.getHeight() >= 335;
	}
	
	public boolean isDead() { return isDead; }
	public void kill() { isDead = true; }
	public void revive() { isDead = false; }
		
	@Override
	public void render(Graphics2D g2d) {
		Graphics g = (Graphics) g2d;
		
		if (isGrounded())
			g.drawImage(super.getAnimation().getCurrentImage(), (int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight(), null);
		else g.drawImage(super.getImage(), (int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight(), null);
		
		if (Game.renderingHitBox) {
			g.setColor(Color.RED);
			g.drawRect((int)super.getX(), (int)super.getY(), super.getWidth(), super.getHeight());
		}
	}

	public double getScore() { return score; }
	public void setScore(double score) { this.score = score; }

	public Agent getAgent() { return agent; }
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
}
