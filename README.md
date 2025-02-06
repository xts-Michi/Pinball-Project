# 🎮 Pinball Physics Project 
**A Java-based pinball game using JBox2D physics engine**  
[![GitHub Stars](https://img.shields.io/github/stars/xts-Michi/Pinball-Project?style=social)](https://github.com/xts-Michi/Pinball-Project/stargazers)  
*Project by xts-Michi*

https://github.com/xts-Michi/Pinball-Project

## 🌟 Features
- **Real Physics Simulation** using JBox2D engine
- **Interactive Elements**:
  - 6 Dynamic flippers with joint mechanics
  - Rotating windmill obstacles
  - Curved tube structures
  - Multiple scoring circles (50-100 points)
- **Full Game Cycle**:
  - Start screen with blinking animation
  - In-game HUD with lives (❤️) and score
  - Game over screen with restart options
- **Visual Effects**:
  - Custom sprite graphics
  - Dynamic camera tracking
  - Smooth animations (60 FPS)
- **Game Mechanics**:
  - Ball speed limiting system
  - Multi-contact scoring detection
  - Life system with respawn mechanics

## 🛠️ Installation

### Requirements:
- Java JDK 11+
- JBox2D library (included in build path)
- Images folder with game assets

### Run in IDE:
1. Import as Maven/Gradle project
2. Add JBox2D to classpath  
3. Run `Game.java` as main class

## 🕹️ How to Play

**Objective**: Keep the ball in play and score maximum points!

**Controls**:
| Action          | Key           |
|-----------------|---------------|
| Activate Flippers | SPACE BAR    |
| Reset Ball      | Automatic     |

**Scoring**:
- Small circles: 20-50 points
- Large circles: 100 points
- Windmill passes: 25 points
- Tube navigation: 50 points

**Lives System**:
- Start with 3 lives (❤️)
- Lose life when ball falls below flippers
- Game over at 0 lives

## 🏗️ Project Architecture

### Core Components
| Class       | Responsibility                          |
|-------------|-----------------------------------------|
| GameLogic   | Manages physics world, collisions, game state |
| GamePanel   | Main game rendering (700x700px canvas)  |
| Ball        | Dynamic ball entity with physics properties |
| Flipper     | Motorized flipper with revolute joints  |
| Windmill    | Rotating obstacle with motor control    |
| Tube        | Bézier-curve collision structure        |

### Support Classes
| Class          | Functionality                      |
|----------------|-------------------------------------|
| StartPanel     | Entry screen with animated UI      |
| EndPanel       | Game over screen with score display|
| ScoringCircle  | Point-generating collision zones   |
| Wall           | Static boundary walls              |

## 📁 Asset Requirements

Project requires `/images` directory containing:

| Asset                 | Purpose                      |
|-----------------------|------------------------------|
| ball_new.png          | Main ball sprite             |
| game_background_v.3.jpg | Game background            |
| title_screen_V.3.jpg  | Start screen background      |
| end_screen_V.1.jpg    | Game over background         |
| heart.png             | Life counter icon            |
| metal_bar.png         | Windmill arm texture         |
| bat.png               | Flipper texture              |
| FlipperJoint.png      | Flipper base sprite          |

## 🚀 Physics Implementation

**Example from GameLogic.java**:
- world = new World(new Vec2(0, 9f)); // Gravity vector
- bodyDef.type = BodyType.DYNAMIC;    // Dynamic physics bodies
- fixtureDef.restitution = 0.8f;      // Bounciness factor
- jointDef.motorSpeed = 30f;          // Flipper rotation speed

**Key Physics Parameters**:
- Gravity: 9 m/s² downward
- Ball density: 1.0 kg/m³
- Flipper torque: 20 N·m
- Max ball speed: 8 m/s

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/foo`)
3. Commit changes (`git commit -am 'Add foo'`)
4. Push to branch (`git push origin feature/foo`)
5. Open Pull Request

## 📜 License

MIT License - See [LICENSE](LICENSE) file for details

---

*Happy Flipping!* 🚀
