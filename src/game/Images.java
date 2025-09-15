package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Images {

	public static final HashMap<String,BufferedImage> images = new HashMap<String,BufferedImage> ();
	
	public static void load() {
	
		if (!images.isEmpty()) {
			System.err.println("Already Loaded!");
			return;
		}
		
		try {
			
			//Small Cactus
			BufferedImage small1 = ImageIO.read(new File("Assets/Cactus/Small1.png"));
			BufferedImage small2 = ImageIO.read(new File("Assets/Cactus/Small2.png"));
			BufferedImage small3 = ImageIO.read(new File("Assets/Cactus/Small3.png"));
			images.put("small1", small1);
			images.put("small2", small2);
			images.put("small3", small3);
			
			//Large Cactus
			BufferedImage large1 = ImageIO.read(new File("Assets/Cactus/Large1.png"));
			BufferedImage large2 = ImageIO.read(new File("Assets/Cactus/Large2.png"));
			BufferedImage large3 = ImageIO.read(new File("Assets/Cactus/Large3.png"));
			images.put("large1", large1);
			images.put("large2", large2);
			images.put("large3", large3);
			
			//Ground
			BufferedImage ground1 = ImageIO.read(new File("Assets/Ground/Ground1.png"));
			BufferedImage ground2 = ImageIO.read(new File("Assets/Ground/Ground2.png"));
			BufferedImage ground3 = ImageIO.read(new File("Assets/Ground/Ground3.png"));
			images.put("ground1", ground1);
			images.put("ground2", ground2);
			images.put("ground3", ground3);
			
			//Dino
			BufferedImage run1 = ImageIO.read(new File("Assets/Dino/Run1.png"));
			BufferedImage run2 = ImageIO.read(new File("Assets/Dino/Run2.png"));
			BufferedImage run3 = ImageIO.read(new File("Assets/Dino/Run3.png"));
			BufferedImage run4 = ImageIO.read(new File("Assets/Dino/Run4.png"));
			BufferedImage jump = ImageIO.read(new File("Assets/Dino/Jump.png"));
			BufferedImage duck1 = ImageIO.read(new File("Assets/Dino/Duck1.png"));
			BufferedImage duck2 = ImageIO.read(new File("Assets/Dino/Duck2.png"));
			images.put("run1", run1);
			images.put("run2", run2);
			images.put("run3", run3);
			images.put("run4", run4);
			
			images.put("jump", jump);
			images.put("duck1", duck1);
			images.put("duck2", duck2);
			
			//Bird
			BufferedImage bird1 = ImageIO.read(new File("Assets/Bird/Bird1.png"));
			BufferedImage bird2 = ImageIO.read(new File("Assets/Bird/Bird2.png"));
			images.put("bird1", bird1);
			images.put("bird2", bird2);
			
		} catch (IOException e) { e.printStackTrace(); }
		
	}
	
}
