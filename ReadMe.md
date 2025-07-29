
-# GameDev-Project01
+# Space Invaders - GameDev Project 01
+
+A Java-based space shooter game featuring multiple scenes and diverse enemy types with random spawning mechanics.
+
+## Table of Contents
+- [Game Overview](#game-overview)
+- [Scenes](#scenes)
+- [Enemy Types](#enemy-types)
+- [Controls](#controls)
+- [Features](#features)
+- [Technical Details](#technical-details)
+- [Installation & Running](#installation--running)
+
+## Game Overview
+
+This is a classic space invaders-style arcade game where players control a spaceship to battle waves of enemies. The game features multiple levels (scenes) with increasing difficulty, random enemy spawning, and various enemy types each with unique behaviors and attack patterns.
+
+**Objective**: Survive enemy waves, destroy targets, and progress through increasingly challenging levels.
+
+## Scenes
+
+### 1. Title Scene
+- **Purpose**: Main menu and game introduction
+- **Features**: 
+  - Animated title screen with blinking "Press SPACE to Start" text
+  - Background music (`title.wav`)
+  - Game credits display
+- **Controls**: 
+  - `SPACE` - Start the game (proceed to Scene 1)
+
+### 2. Scene 1 - First Battle Zone
+- **Description**: The opening level featuring basic enemy encounters
+- **Environment**: Simple black space background
+- **Enemy Spawn Rate**: Every 120 frames (~2 seconds at 60 FPS)
+- **Victory Condition**: Destroy 24 enemies (configurable via `NUMBER_OF_ALIENS_TO_DESTROY`)
+- **Features**:
+  - Real-time enemy kill counter
+  - Frame counter display
+  - Random enemy type and position spawning
+  - Audio: `Scene1Audio.mp3`
+- **Progression**: Press `N` when level complete to advance to Scene 2
+
+### 3. Scene 2 - Star Field Sector
+- **Description**: Advanced level with enhanced visuals and faster enemy spawning
+- **Environment**: Animated star field background with scrolling star clusters
+- **Enemy Spawn Rate**: Every 100 frames (~1.67 seconds at 60 FPS)
+- **Visual Features**:
+  - Dynamic scrolling star field background
+  - Animated star clusters
+  - Enhanced visual effects
+- **Audio**: `Scene2Audio.mp3`
+- **Increased Difficulty**: Faster enemy spawning creates more intense gameplay
+
+## Enemy Types
+
+### 1. ShootingEnemy 🔫
+**Appearance**: UFO-style spacecraft with rotating animation
+- **Behavior**: 
+  - Moves in predictable patterns while descending
+  - Actively tracks and targets the player
+  - Fires energy shots at regular intervals
+- **Attack Pattern**: Direct projectile shots aimed at player position
+- **Projectile Type**: `EnemyShot` - fast-moving energy bullets
+- **Strategy**: Medium threat level - dangerous in groups
+- **Animation**: Uses UFO sprite sequence (`IMG_UFO01` through `IMG_UFO06`)
+
+### 2. MissileEnemy 🚀
+**Appearance**: Military-style spacecraft
+- **Behavior**:
+  - Slower movement but more aggressive targeting
+  - Calculates player position for precision strikes
+  - Launches homing missiles
+- **Attack Pattern**: Guided missile strikes with tracking capability
+- **Projectile Type**: `Missile` - slower but tracking projectiles
+- **Strategy**: High threat level - missiles follow player movement
+- **Special Ability**: Missiles can change direction to pursue the player
+- **Animation**: Military spacecraft design with missile launch effects
+
+### 3. BombEnemy 💣
+**Appearance**: Pirate ship-style design
+- **Behavior**:
+  - Drops explosive bombs directly below its position
+  - Focuses on area denial rather than precision targeting
+  - Bombs explode on impact or after timeout
+- **Attack Pattern**: Gravity-based bomb dropping with explosion radius
+- **Projectile Type**: `Bomb` - explosive projectiles with area damage
+- **Strategy**: Area control enemy - bombs create dangerous zones
+- **Special Features**:
+  - Bombs have explosion animations
+  - Area-of-effect damage
+  - Can block player movement paths
+- **Animation**: Uses pirate ship sprites (`IMG_PIRATE01` through `IMG_PIRATE05`)
+
+## Controls
+
+### Player Movement
+- `LEFT ARROW` / `A` - Move ship left
+- `RIGHT ARROW` / `D` - Move ship right
+- `UP ARROW` / `W` - Move ship up
+- `DOWN ARROW` / `S` - Move ship down
+
+### Combat
+- `SPACE` - Fire player shots (maximum 4 shots on screen simultaneously)
+
+### Navigation
+- `N` - Next level (when level complete message appears)
+- `ESC` - Exit game immediately
 
+## Features
+
+### Random Spawning System
+- **Algorithm**: Uses `Random.nextInt(3)` for enemy type selection
+- **Position**: `Random.nextInt(BOARD_WIDTH)` for horizontal spawn position
+- **Timing**: Frame-based counters with different intervals per scene
+- **Benefits**: High replayability, unpredictable challenge
+
+### Combat System
+- **Collision Detection**: Pixel-perfect collision using sprite boundaries
+- **Health System**: One-hit destruction for both player and enemies
+- **Shot Limitation**: Maximum 4 player shots on screen prevents spam
+- **Enemy Projectiles**: Each enemy type uses different projectile systems
+
+### Visual Effects
+- **Sprite Animation**: Multi-frame animations for ships and effects
+- **Explosion Effects**: Death animations for all entities
+- **Background Elements**: Scene 2 features animated star field
+- **UI Elements**: Real-time statistics display
+
+### Audio System
+- **Scene-Specific Music**: Different background tracks for each scene
+- **File Formats**: Supports MP3 and WAV audio files
+- **Audio Management**: Proper cleanup when switching scenes
+
+## Technical Details
+
+### Architecture
+- **Language**: Java with Swing/AWT graphics
+- **Design Pattern**: Scene-based architecture with separate classes
+- **Frame Rate**: 60 FPS using `Timer(1000/60, ActionListener)`
+- **Resolution**: 716×700 pixels (scalable via `SCALE_FACTOR`)
+
+### File Structure
+```
+src/
+├── gdd/
+│   ├── Game.java              # Main game controller
+│   ├── Global.java            # Constants and configuration
+│   ├── AudioPlayer.java       # Audio system
+│   ├── SpawnDetails.java      # Enemy spawn configuration
+│   ├── scene/
+│   │   ├── TitleScene.java    # Main menu
+│   │   ├── Scene1.java        # First level
+│   │   └── Scene2.java        # Second level
+│   ├── sprite/
+│   │   ├── Player.java        # Player ship
+│   │   ├── Enemy.java         # Base enemy class
+│   │   ├── ShootingEnemy.java # Shooting enemy type
+│   │   ├── MissileEnemy.java  # Missile enemy type
+│   │   ├── BombEnemy.java     # Bomb enemy type
+│   │   ├── PlayerShot.java    # Player projectiles
+│   │   ├── EnemyShot.java     # Enemy bullets
+│   │   ├── Missile.java       # Homing missiles
+│   │   └── Bomb.java          # Explosive bombs
+│   └── powerup/
+│       └── SpeedUp.java       # Power-up items
+├── images/                    # Sprite graphics
+├── animation/                 # Animation frames
+└── audio/                     # Sound effects and music
+```
+
+### Configuration Constants (Global.java)
+- `BOARD_WIDTH`: 716 pixels
+- `BOARD_HEIGHT`: 700 pixels  
+- `NUMBER_OF_ALIENS_TO_DESTROY`: 24
+- `ALIEN_WIDTH/HEIGHT`: Enemy hitbox dimensions
+- Various sprite file paths and game parameters
+
+## Installation & Running
+
+### Prerequisites
+- Java Development Kit (JDK) 8 or higher
+- IDE supporting Java projects (IntelliJ IDEA, Eclipse, etc.)
+
+### Setup Instructions
+1. Clone or download the project
+2. Open in your preferred Java IDE
+3. Ensure all asset files (images, audio) are in correct directories
+4. Compile and run `Game.java` as the main class
+
+### Troubleshooting
+- **Audio Issues**: Verify audio files exist in `src/audio/` directory
+- **Graphics Issues**: Check image paths in `Global.java`
+- **Performance**: Adjust `DELAY` constant for different frame rates
+
+## Game Tips & Strategy
+
+### Survival Tips
+- **Movement**: Keep moving to avoid enemy projectiles
+- **Positioning**: Use screen edges strategically
+- **Target Priority**: Focus on ShootingEnemies first (highest threat)
+- **Bomb Avoidance**: Watch for BombEnemy drop patterns
+
+### Advanced Strategies
+- **Shot Management**: Don't spam - manage your 4-shot limit efficiently
+- **Pattern Recognition**: Learn enemy movement patterns
+- **Safe Zones**: Identify areas with less enemy activity
+- **Level Transitions**: Clear all remaining enemies before advancing
+
+## Recent Updates
+
+### Random Enemy Spawning Implementation
+- **Scene 1**: Enemies now spawn randomly every 120 frames with random positions
+- **Scene 2**: Enhanced with random spawning every 100 frames
+- **Enemy Variety**: All three enemy types (Shooting, Missile, Bomb) spawn randomly
+- **Improved Replayability**: Each playthrough offers unique enemy patterns
+
+---
+
+**Developer**: 
Pawat Asavapotiphan 6714522
Sai Sy Hein 6420131

+**Version**: 1.0  
+**Last Updated**: July 2025
+
+
