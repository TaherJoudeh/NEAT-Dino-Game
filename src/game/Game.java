package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import gameObjects.Ground;
import gameObjects.Obstacle;
import gameObjects.Player;
import input.KeyInput;
import main.java.neat.config.ActivationConfig;
import main.java.neat.config.ActivationConfigBuilder;
import main.java.neat.config.AggregationConfig;
import main.java.neat.config.NEATConfig;
import main.java.neat.config.NEATConfig.CONNECTIVITY;
import main.java.neat.config.NEATConfig.SELECTION_TYPE;
import main.java.neat.config.NEATConfig.SPECIES_FITNESS_FUNCTION;
import main.java.neat.config.NEATConfigBuilder;
import main.java.neat.core.Agent;
import main.java.neat.core.Genome;
import main.java.neat.core.Neat;
import main.java.neat.functions.ActivationFunction.ACTIVATION_FUNCTION;
import main.java.neat.functions.AggregationFunction.AGGREGATION_FUNCTION;
import main.java.neat.io.GenomeFileHandler;
import main.java.neat.visualizer.GenomeVisualizer;
import main.java.neat.visualizer.GenomeVisualizerBuilder;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private boolean isRunning;
	
	private final int MAX_TIME = 50000;
	private int timer1, timer2;
	private boolean maximumStepsReached;
	
	public final static float GRAVITY = 0.06f;

	private GameObjectHandler handler;
	private Ground ground;
	
	private Spawner spawner;
	
	private KeyInput keyInput;
	
	public static int score, toCrossover = 50, highScore, gen = 1;
	private int count;
	
	public static float speed = -1.5f;
	private boolean speedChange;
	
	public static boolean isAI = true, restart = true, renderingHitBox = false;
	public static boolean test = true;
	
	private final int POPULATION = test ? 1 : 150;
	private Player[] players = new Player[POPULATION];

	private NEATConfig nc;
	private Neat neat;
	private GenomeVisualizer gv;
	
	public Game() {
		
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		thread = new Thread(this);
	
		Images.load();
		
		handler = new GameObjectHandler();
		ground = new Ground(0,330,speed,0,Window.WIDTH,14,Images.images.get("ground1"),handler);

		initNEAT();
		Agent[] agents = neat.getPopulation();
		
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(50,296,0,0,40,43,null,handler);
			players[i].setAgent(agents[i]);
			handler.addGameObject(players[i]);
		}

		if (test) {
			Genome genome = GenomeFileHandler.loadGenome("best.neat");
			players[0].getAgent().setGenome(genome);
		}
		
		spawner = new Spawner(speed,300,handler);
		
		handler.addGameObject(ground);
		
		keyInput = new KeyInput(handler);
		addKeyListener(keyInput);
		
	}
	
	private void initNEAT() {
		
		ActivationConfig acConf = new ActivationConfigBuilder()
				.addActivationFunction(ACTIVATION_FUNCTION.SIGMOID)
				.addActivationFunction(ACTIVATION_FUNCTION.RELU)
				.setReluLeak(0.1)
				.build();
		AggregationConfig agConf = new AggregationConfig(AGGREGATION_FUNCTION.SUM);
		nc = new NEATConfigBuilder(POPULATION, 5, 3, agConf, acConf)
				.setActivationDefault(ACTIVATION_FUNCTION.RELU)
				.setAggregationDefault(AGGREGATION_FUNCTION.SUM)
				.setStartingActivationFunctionForHiddenNodes(ACTIVATION_FUNCTION.RELU)
				.setStartingActivationFunctionForOutputNodes(ACTIVATION_FUNCTION.SIGMOID)
				.setStartingAggregationFunction(AGGREGATION_FUNCTION.SUM)
				
				.setSpeciesFitnessFunction(SPECIES_FITNESS_FUNCTION.MEAN)
				
				.setFitnessTermination(true)
				.setFitnessTerminationThreshold(20000)
				
				.setBiasInitStdev(3)
				.setBiasMaxValue(3)
				.setBiasMinValue(-3)
				.setBiasAdjustingRate(0.8)
				.setBiasRandomizingRate(0.1)
				.setBiasMutationPower(0.15)
				
				.setWeightInitStdev(3)
				.setWeightMaxValue(3)
				.setWeightMinValue(-3)
				.setWeightAdjustingRate(0.8)
				.setWeightRandomizingRate(0.1)
				.setWeightMutationPower(0.15)
				
				.setCompatibilityThreshold(5)
				.setCompatibilityDisjointCoefficient(1)
				.setCompatibilityExcessCoefficient(1)
				.setCompatibilityWeightCoefficient(0.5)
				.setDynamicCompatabilityThreshold(true)
				.setCompatabilityThresholdAdjustingFactor(0.2)
				.setTargetNumberOfSpecies(12)
				.setStagnation(20)
				
				.setInitConnectivity(CONNECTIVITY.PARTIAL_NO_DIRECT)
				.setProbConnectInit(0.6)
				.setSurvivalThreshold(0.3)
				.setSelectionType(SELECTION_TYPE.TOURNAMENT)
				.setTournamentSize(3)
				.setSpeciesElitism(2)
				.setElitism(1)
				
				.setEnabledMutationRate(0.05)
				.setEnabledRateForEnabled(-0.05)
				
				.setProbAddConnection(0.07)
				.setProbAddNode(0.04)
				
				.build();
		
		neat = new Neat(nc);
		
		gv = new GenomeVisualizerBuilder().defaultGenomeVisuals().setNodeSize(15).build();
		
	}
	
	public synchronized void start() {
		
		if (isRunning) return;
		isRunning = true;
		thread.start();
		
	}
	public synchronized void stop() {
		
		if (!isRunning) return;
		isRunning = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) { e.printStackTrace(); }
		System.exit(0);
		
	}
	
	@Override
	public void run() {

		while (isRunning) {
			
			update();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			render();
			
		}
		
	}

	private void update() {
		
		if (gameOver() || (maximumStepsReached && !test)) {
						
			if (isAI) {
				
				if (!test) {
					neat.evolve(true);
					if (neat.isTerminated()) {
						GenomeFileHandler.saveGenome(neat.getBest().getGenome(), null, "best");
						GenomeFileHandler.saveImage(
								GenomeVisualizer.visualizeGenome(new GenomeVisualizerBuilder().defaultGenomeVisuals().build(), "#000000", false, true, neat.getBest().getGenome(), nc.getWeightMaxValue(), 500, 500)
								, null, "best", "png");
						stop();
					}
				}
				if (score > highScore)
					highScore = score;
				
				reset();
				Player.deadPlayers.clear();
				
			}else if (restart)
				reset();

			count = 0;
			score = 0;
			
		}else {
		
			if (score > highScore)
				highScore = score;
			
			handler.updateGameObjects();

			if (count%30 == 0 && count != 0) {
				if (score%1000 == 0 && score != 0) {
					speedChange = true;
				}
				score++;
				count = 0;
			}
		
			if (speedChange && Obstacle.obs.isEmpty()) {
				ground.setVelX(Math.max(-5f, ground.getVelX()-0.2f));
				spawner.setVelX(Math.max(-5f, spawner.getVelX()-0.2f));
				speedChange = false;
			}
		
			count++;
		
			if (!speedChange)
				spawner.spawn();
			checkCollision();
		
			if (isAI)
				startNN();
		
			timer1++;
		
			if (timer1%20 == 0 && timer1 != 0) {
				timer2++;
				timer1 = 0;
			}
		
			if (timer2 == MAX_TIME) {
				maximumStepsReached = true;
				timer1 = 0;
				timer2 = 0;
			}
		}		
	}
	
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		//Background
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);
		
		//Objects
		handler.renderGameObjects(g2d);
		
		if (neat.getBest() != null) {
			Genome bestBrain = neat.getBest().getGenome();
			BufferedImage bestBrainImage = GenomeVisualizer.visualizeGenome(gv, null, true, true, bestBrain, nc.getWeightMaxValue(), 300, 200);
			g2d.drawImage(bestBrainImage, 220, 2, null);
		}
		
		if (test) {
			BufferedImage bestBrainImage = GenomeVisualizer.visualizeGenome(gv, null, true, true, neat.getPopulation()[0].getGenome(), nc.getWeightMaxValue(), 300, 200);
			g2d.drawImage(bestBrainImage, 220, 2, null);
		}
		
		//HUD
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial",Font.BOLD,20));
		g2d.drawString("Score: " + score, 0, 20);
		
		if (score >= highScore)
			g2d.setColor(Color.YELLOW);
		g2d.drawString("HighScore: " + highScore, 0, 41);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Players Left: " + (POPULATION-Player.deadPlayers.size()), 0, 62);
		g2d.drawString("Generation: " + neat.getGeneration(), 0, 83);
		
		g2d.dispose();
		bs.show();
		
	}
	
	public boolean gameOver() {
		
		return handler.getPlayer() == null;
			
	}
	
	public void checkCollision() {
		for (int i = 0; i < players.length; i++) {
			for (int j = 0; j < handler.size(); j++) {
				if (handler.getGameObject(j) instanceof Obstacle && !Player.deadPlayers.contains(players[i])) {
				if (players[i].getBounds().intersects(handler.getGameObject(j).getBounds())) {
					players[i].kill();
					players[i].setScore(score);
					handler.removeGameObject(players[i]);
					Player.deadPlayers.push(players[i]);
					break;
				}
				}
			}
		}
	}
	
	public void reset() {
		
		maximumStepsReached = false;
		timer1 = 0;
		timer2 = 0;
		
		speedChange = false;
		handler.clearAll();
		spawner.reset();
		ground.setVelX(-1.5f);
		
		Obstacle.obs.clear();
		
		Player.deadPlayers.clear();
		for (int i = 0; i < players.length; i++) {
			players[i].revive();
			players[i].current_state = Player.PLAYER_RUN;
			players[i].setY(296);
			handler.addGameObject(players[i]);
		}
		
		handler.addGameObject(ground);
		
	}
	
	public static void main(String[] args) {
		new Window("GoogleDino Game",new Game());
	}
	
	public void startNN() {
		
		for (int i = 0; i < players.length; i++) {
			
			if (players[i].isDead())
				continue;
			
			double[] inputs = getInputs(players[i]);

			double fitness = score;
			players[i].getAgent().setFitness(fitness);
			double[] decide = players[i].getAgent().think(inputs);
				
			double activateThreshold = 0.7;
				
			if (decide[0] > decide[1] && decide[0] > decide[2] && decide[0] > activateThreshold)
				players[i].bigJump();
			else if (decide[1] > decide[0] && decide[1] > decide[2] && decide[1] > activateThreshold)
				players[i].smallJump();
			else if (decide[2] > decide[0] && decide[2] > decide[1] && decide[2] > activateThreshold) {
				if (players[i].isGrounded())
					players[i].current_state = Player.PLAYER_DUCK;
				else players[i].current_state = Player.PLAYER_JUMP_DOWN;
			}
			else players[i].current_state = Player.PLAYER_RUN;
			
		}
		
	}
	
	public double[] getInputs(Player player) {
		
		double[] inputs = new double[5];
		double playerEndX = player.getX()+player.getWidth();
				
		if (!Obstacle.obs.isEmpty()) {
		
			inputs[0] = Obstacle.obs.get(0).getX()-playerEndX;
			inputs[1] = Obstacle.obs.get(0).getWidth();
			inputs[2] = ground.getY()-(Obstacle.obs.get(0).getY()+Obstacle.obs.get(0).getHeight());
			inputs[3] = Obstacle.obs.get(0).getHeight();
		
		}else {
			inputs[0] = Window.WIDTH-playerEndX;
			inputs[1] = 0;
			inputs[2] = 0;
			inputs[3] = 0;
		}
		inputs[4] = -spawner.getVelX();
		
		return inputs;
	}
	
}