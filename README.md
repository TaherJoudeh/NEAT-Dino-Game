# NEAT Dino Game

AI agents learn to play Chrome Dino Game using NEAT (NeuroEvolution of Augmenting Topologies) with real-time neural network visualization.

![2025-09-1520-48-02-ezgif com-video-to-gif-converter](https://github.com/user-attachments/assets/dd2ca7ca-4b78-4be7-baec-c80db2d32ba2)

*The Best Genome!*

**Built with [NEAT4J](https://github.com/TaherJoudeh/NEAT4J)**

## Features

- **150 Agent Population** - Simultaneous evolution of multiple AI players
- **Real-time Network Visualization** - See the best brain structure during training  
- **Complete Game Engine** - Full Dino game with multiple obstacle types
- **Pre-trained Models** - Load saved networks with `best.neat` files
- **Manual Play Mode** - Switch between AI and human control
- **Smart Sensors** - 5-input system analyzing obstacles and player state

## Quick Start

### Prerequisites
- Java 8+
- NEAT4J library - Download from [NEAT4J repository](https://github.com/TaherJoudeh/NEAT4J)

### Installation
```bash
git clone https://github.com/TaherJoudeh/NEAT-Dino-Game.git
cd NEAT-Dino-Game
# Add NEAT4J JAR to your project classpath
```

### Run Pre-trained Agent
```bash
mvn exec:java -Dexec.mainClass="game.Game"
```
*Make sure `best.neat` file exists in the project root*

### Train from Scratch
Set `test = false` in `Game.java` line 44, then run:
```bash
mvn exec:java -Dexec.mainClass="game.Game"
```

## How It Works

### AI Inputs (5 sensors)
- Distance to next obstacle
- Obstacle width  
- Obstacle height gap from ground
- Obstacle total height
- Current game speed

### AI Outputs (3 actions)
- **Big Jump** - High jump for tall obstacles
- **Small Jump** - Quick hop for low obstacles  
- **Duck/Dive** - Crouch or fast-fall to avoid birds

### Game Mechanics
- **Cactus Obstacles** - 6 different types (small/large variants)
- **Flying Birds** - 3 different heights requiring ducking
- **Progressive Difficulty** - Speed increases every 1000 points
- **Fitness Function** - Based on distance traveled (score)

## Project Structure

```
src/
├── game/
│   ├── Game.java              # Main game loop and NEAT integration
│   ├── GameObjectHandler.java # Object management system  
│   ├── Spawner.java           # Obstacle generation logic
│   ├── Animation.java         # Sprite animation system
│   ├── Images.java            # Asset loading and management
│   └── Window.java            # GUI window setup
├── gameObjects/
│   ├── GameObject.java        # Base class for all game entities
│   ├── Player.java            # Dino player with AI agent
│   ├── Obstacle.java          # Cactus obstacles  
│   ├── Bird.java              # Flying bird obstacles
│   └── Ground.java            # Scrolling ground tiles
└── input/
    └── KeyInput.java          # Manual control handling
```

## Configuration

### NEAT Parameters (in `Game.java`)
```java
POPULATION = 150              // Number of agents per generation  
setCompatibilityThreshold(5)  // Species separation
setProbAddConnection(0.07)    // Connection mutation rate
setProbAddNode(0.04)          // Node addition rate
setFitnessTerminationThreshold(20000) // Stop condition
```

### Game Settings
```java
MAX_TIME = 50000             // Maximum steps per generation
GRAVITY = 0.06f              // Jump physics
speed = -1.5f                // Initial scroll speed
```

## Controls

**During Manual Play:**
- `Space` - Big jump
- `Up Arrow` - Small jump  
- `Down Arrow` - Duck/dive
- `Q` - Toggle hitboxes
- `Enter` - Restart game

**AI Mode:**
- Networks automatically control all actions
- Real-time visualization shows best performing brain

## Assets Required

Place sprite files in `Assets/` directory:
```
Assets/
├── Cactus/
│   ├── Small1.png, Small2.png, Small3.png
│   └── Large1.png, Large2.png, Large3.png  
├── Dino/
│   ├── Run1.png, Run2.png, Run3.png, Run4.png
│   ├── Jump.png
│   └── Duck1.png, Duck2.png
├── Bird/
│   └── Bird1.png, Bird2.png
└── Ground/
    └── Ground1.png, Ground2.png, Ground3.png
```

## Advanced Features

- **Network Visualization** - GenomeVisualizer shows active connections
- **Species Evolution** - NEAT maintains diversity through speciation  
- **Elitism** - Best performers automatically survive to next generation
- **Dynamic Compatibility** - Automatic species threshold adjustment
- **Model Persistence** - Save/load trained networks as `.neat` files

## Performance Tips

- Set `test = true` for single-agent demo mode
- Adjust population size based on your hardware capabilities
- Increase `MAX_TIME` for longer training episodes
- Modify fitness termination threshold for different performance targets

## Credits

- **NEAT4J** - [https://github.com/TaherJoudeh/NEAT4J](https://github.com/TaherJoudeh/NEAT4J)
- **Original NEAT Algorithm** - Kenneth O. Stanley, University of Texas
- **Game Assets** - Chrome Dino Game sprites
