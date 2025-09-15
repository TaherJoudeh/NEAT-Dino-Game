package game;

import java.awt.Graphics2D;
import java.util.LinkedList;

import gameObjects.GameObject;
import gameObjects.Obstacle;
import gameObjects.Player;

public class GameObjectHandler {

	private LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();
	
	//Size
	public int size() { return gameObjects.size(); }
	
	//ADD
	public boolean addGameObject(GameObject gameObject) { return gameObjects.add(gameObject); }
	
	//GET
	public GameObject getGameObject(int index) { return gameObjects.get(index); }
	
	//REMOVE
	public GameObject removeGameObject(int index) { return gameObjects.remove(index); }
	public boolean removeGameObject(GameObject gameObject) { return gameObjects.remove(gameObject); }
	
	//GET PLAYER
	public Player getPlayer() {
		for (int i = 0; i < gameObjects.size(); i++)
			if (gameObjects.get(i) instanceof Player) return (Player) gameObjects.get(i);
		return null;
	}
	
	//UPDATE
	public void updateGameObjects() {
		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).update();
	}
	
	//RENDER
	public void renderGameObjects(Graphics2D g2d) {
		for (int i = 0; i < gameObjects.size(); i++)
			gameObjects.get(i).render(g2d);
	}

	//CLEAR
	public void clearAll() { gameObjects.clear(); }
	public void clearObstacles() {
		for (int i = 0; i < gameObjects.size(); i++) {
			if (gameObjects.get(i) instanceof Obstacle) gameObjects.remove(i);
		}
	}
	
}
