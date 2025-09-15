package input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import game.Game;
import game.GameObjectHandler;
import gameObjects.Player;

public class KeyInput extends KeyAdapter {

	private Player player;
		
	public KeyInput(GameObjectHandler handler) {
		player = handler.getPlayer();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_Q) {
			if (Game.renderingHitBox) Game.renderingHitBox = false;
			else Game.renderingHitBox = true;
		}
		
		if (Game.isAI) return;
		
		if (key == KeyEvent.VK_SPACE && player.current_state != Player.PLAYER_DUCK) player.bigJump();
		if (key == KeyEvent.VK_UP && player.current_state != Player.PLAYER_DUCK) player.smallJump();
		
		if (key == KeyEvent.VK_DOWN && player.isGrounded()) player.current_state = Player.PLAYER_DUCK;
		if (key == KeyEvent.VK_DOWN && !player.isGrounded()) player.current_state = Player.PLAYER_JUMP_DOWN;
		
		if (key == KeyEvent.VK_ENTER && !Game.restart) Game.restart = true;
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (Game.isAI) return;
		
		if (key == KeyEvent.VK_UP && player.isGrounded()) player.smallJump();
		if (key == KeyEvent.VK_DOWN) player.current_state = Player.PLAYER_RUN;
	
	}
	
}
