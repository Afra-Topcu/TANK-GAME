# Tank 2025 - JavaFX 2D Game 🚀🛡️

A classic 2D arcade-style tank shooter game developed using the JavaFX framework. The player navigates a walled battlefield, dodging enemy fire, and destroying enemy tanks to accumulate points while managing limited lives. 

## 📝 Project Overview

This project was developed to master **Graphical User Interfaces (GUI)** and **Event-Driven Programming** in Java using JavaFX. It departs from standard command-line applications by implementing a continuous, real-time game loop (`AnimationTimer`) and handling multi-threaded rendering and logic updates.

### 🎥 Gameplay Demo
Watch the game in action here: **[Tank 2025 - Gameplay Demo](https://youtu.be/f7qM1CKFrpg?si=Vx2uStpC4HvX0p1d)**

## 🚀 Technical Features

- **Custom Game Engine:** Hand-coded game loop utilizing JavaFX's `AnimationTimer` for smooth rendering and logic processing.
- **Robust Collision Detection:** Implements a unified `GameInterface` allowing polymorphic collision checks between the `PlayerTank`, `EnemyTank`, `Bullet`, and `Walls`.
- **Enemy AI:** Enemy tanks possess basic artificial intelligence; they navigate the grid, detect obstacles, randomly change directions, and fire dynamically.
- **Visual Effects:** Features dynamic rendering of sprites and temporary visual states, including `Explosion` and `BigExplosion` animations triggered by projectile impacts.
- **State Management:** Real-time tracking of Score and Lives, with seamless Pause/Resume and Restart capabilities.

## 🎮 Game Controls

- **Arrow Keys (Up/Down/Left/Right):** Move the tank.
- **`X` Key:** Fire bullet.
- **`P` Key:** Pause / Resume the game.
- **`R` Key:** Restart the game.
- **`ESC`:** Exit the game.

## 🛠️ Installation & Usage

*Note: Since this is a JavaFX application, you must have the JavaFX SDK configured in your environment or IDE to compile and run the project.*

1. **Clone the repository:**

```bash
git clone [https://github.com/Afra-Topcu/TANK-GAME.git](https://github.com/Afra-Topcu/TANK-GAME.git)
```

2. **Ensure Assets are Present:**
Make sure the `assets/` folder (containing `yellowTank1.png`, `whiteTank1.png`, `wall.png`, `bullet.png`, etc.) is located in the root directory of the project.

3. **Run the application:**
Execute the `Main` class. The game will launch in a `900x600` window.

---

*Developed as a Computer Engineering student at Hacettepe University.*