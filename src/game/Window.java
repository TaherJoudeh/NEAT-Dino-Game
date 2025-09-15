package game;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	private JFrame jframe;
	public final static int WIDTH = 800, HEIGHT = 500;
	public final static Dimension size = new Dimension(WIDTH,HEIGHT);
	
	public Window(String title, Game game) {
		
		game.setMaximumSize(size);
		game.setPreferredSize(size);
		game.setMinimumSize(size);
		
		jframe = new JFrame(title);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(game);
		jframe.setResizable(false);
		jframe.setSize(size);
		jframe.setLocation(230, 120);
		jframe.setVisible(true);
		
		game.start();
		
	}
	
}
