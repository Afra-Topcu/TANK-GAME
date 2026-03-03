import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import java.util.*;
import javafx.scene.image.ImageView;

class GameController {
    private Pane root;
    private PlayerTank playerTank;
    private List<EnemyTank> enemyTanks=new ArrayList<>();
    private List<Bullet> bullets =new ArrayList<>();
    private List<Walls> walls=new ArrayList<>();
    private List<Explosion> explosions=new ArrayList<>();
    private List<BigExplosion> bigExplosions=new ArrayList<>();
    private boolean[] pressedKeys =new boolean[256]; //keyboard input tracking
    private Text scoreText;
    private Text livesText;
    private int score=0;
    private int lives=3;
    private boolean gameOver=false;
    private boolean paused=false;
    private Random random=new Random();
    private AnimationTimer gameloop;
    private long lastEnemyMove=0;

    //initializes the game controller with the root pane
    public GameController(Pane root) {
        this.root=root;
        initializeGame();
    }
    //sets up initial game state,IU and starts the game loop
    private void initializeGame() {
        //creates and configure score and live displays
        scoreText=new Text(50,50,"Score: 0");
        livesText=new Text(50,70,"Lives: 3");
        scoreText.setStyle("-fx-fill: #FF69B4; -fx-font-size: 20;"); // Pembe renk
        livesText.setStyle("-fx-fill: #FF69B4; -fx-font-size: 20;");
        //add IU elemnts to the scene
        root.getChildren().addAll(scoreText,livesText);
        //create the walls
        createWalls();
        //initialize player tank at starting position
        playerTank=new PlayerTank(400,500,walls,enemyTanks);
        root.getChildren().add(playerTank.getView());
        //set up the main game loop
        gameloop=new AnimationTimer() {
            private long lastEnemySpawn=0;

            @Override
            public void handle(long now) {
                if(gameOver || paused) return;
                //spawn new enemy every 3 seconds if under limit
                if(now-lastEnemySpawn > 3_000_000_000L){
                    //her 3saniyede
                    spawnEnemy();
                    lastEnemySpawn=now;
                }
                //update all game elements
                moveEnemies();
                updatePlayer();
                updateBullets();
                checkCollisions();
            }
        };
    }

    //creates game walls
    private void createWalls(){
        //create border walls
        for (int x = 0; x < Main.WIDTH; x += 30) {
            walls.add(new Walls(x, 0)); //top border
            walls.add(new Walls(x, Main.HEIGHT-30 )); //bottom walls
        }
        for (int y = 30; y < Main.HEIGHT; y += 30) {
            walls.add(new Walls(0, y)); //left border
            walls.add(new Walls(Main.WIDTH-30, y)); //right border
        }
        //create internal walls
        walls.add(new Walls(130,150));
        walls.add(new Walls(160,150));
        walls.add(new Walls(190,150));
        walls.add(new Walls(220,150));
        walls.add(new Walls(250,150));
        walls.add(new Walls(280,150));
        walls.add(new Walls(310,150));
        walls.add(new Walls(500,150));
        walls.add(new Walls(530,150));
        walls.add(new Walls(560,150));
        walls.add(new Walls(590,150));
        walls.add(new Walls(620,150));
        walls.add(new Walls(650,150));
        walls.add(new Walls(680,150));

        walls.add(new Walls(130,430));
        walls.add(new Walls(160,430));
        walls.add(new Walls(190,430));
        walls.add(new Walls(220,430));
        walls.add(new Walls(250,430));
        walls.add(new Walls(280,430));
        walls.add(new Walls(310,430));
        walls.add(new Walls(500,430));
        walls.add(new Walls(530,430));
        walls.add(new Walls(560,430));
        walls.add(new Walls(590,430));
        walls.add(new Walls(620,430));
        walls.add(new Walls(650,430));
        walls.add(new Walls(680,430));

        walls.add(new Walls(310,180));
        walls.add(new Walls(310,210));
        walls.add(new Walls(280,240));
        walls.add(new Walls(250,240));
        walls.add(new Walls(310,400));
        walls.add(new Walls(310,370));
        walls.add(new Walls(280,340));
        walls.add(new Walls(250,340));

        walls.add(new Walls(500,180));
        walls.add(new Walls(500,210));
        walls.add(new Walls(530,240));
        walls.add(new Walls(560,240));
        walls.add(new Walls(500,400));
        walls.add(new Walls(500,370));
        walls.add(new Walls(530,340));
        walls.add(new Walls(560,340));

        walls.add(new Walls(405,285));
        walls.add(new Walls(435,285));
        walls.add(new Walls(375,285));

        //add walls to the scene
        for(Walls wall:walls){
            root.getChildren().add(wall.getView());
        }
    }

    //spawns a new enemy tank at a valid random position
    private void spawnEnemy() {
        if (enemyTanks.size() >= 10) return;
        int maxAttempts = 100;
        int attempts = 0;
        boolean validPosition = false;
        int x = 0, y = 0;
        //try to find valid spawn position
        while (!validPosition && attempts < maxAttempts) {
            attempts++;
            //generate random position at play area
            x = 100 + random.nextInt(Main.WIDTH - 200);
            y = 100 + random.nextInt(Main.HEIGHT - 200);
            //check if there is a wall or not
            validPosition = true;
            for (Walls wall : walls) {
                if (x < wall.getX() + wall.getWidth() &&
                        x + 40 > wall.getX() &&
                        y < wall.getY() + wall.getHeight() &&
                        y + 40 > wall.getY()) {
                    validPosition = false;
                    break;
                }
            }
            //check other tanks collisions if position still valid
            if (validPosition) {
                for (EnemyTank enemy : enemyTanks) {
                    if (x < enemy.getX() + enemy.getWidth() &&
                            x + 40 > enemy.getX() &&
                            y < enemy.getY() + enemy.getHeight() &&
                            y + 40 > enemy.getY()) {
                        validPosition = false;
                        break;
                    }
                }
            }
            if (validPosition) {
                if (x < playerTank.getX() + playerTank.getWidth() &&
                        x + 40 > playerTank.getX() &&
                        y < playerTank.getY() + playerTank.getHeight() &&
                        y + 40 > playerTank.getY()) {
                    validPosition = false;
                }
            }
        }
        //create enemy tank if valid position is found
        if (validPosition) {
            EnemyTank enemy = new EnemyTank(x,y,walls,playerTank,enemyTanks);
            enemyTanks.add(enemy);
            root.getChildren().add(enemy.getView());
        }
    }

    //updates all enemy tank positions and behaviors
    private void moveEnemies(){
        long now=System.nanoTime();
        for(EnemyTank enemy : enemyTanks){
            //change directions every 2 seconds
            if(now-lastEnemyMove>2_000_000_000L){
                enemy.changeDirection();//random new direction
            }
            enemy.move();
            //random chance to fire
            if (random.nextDouble()<0.03){
                Bullet bullet=enemy.fire();
                bullets.add(bullet);
                root.getChildren().add(bullet.getView());
            }
        }
        if(now-lastEnemyMove>2_000_000_000L){
            lastEnemyMove=now;//reset direction change timer
        }

    }

    //updates player tank based on keyboard input
    private void updatePlayer(){
        if (pressedKeys[37]) playerTank.moveLeft();
        if (pressedKeys[38]) playerTank.moveUp();
        if (pressedKeys[39]) playerTank.moveRight();
        if (pressedKeys[40]) playerTank.moveDown();
    }

    //updates all bullet positions
    private void updateBullets(){
        for(Bullet bullet:bullets){
            bullet.move();
        }
    }

    private void checkCollisions(){
        //check bullet-wall collisions
        bullets.removeIf(bullet -> {
            for(Walls wall:walls){
                if(bullet.collidesWith(wall)){
                    //create small explosion and remove bullet
                    explosions.add(new Explosion(bullet.getX(),bullet.getY()));
                    root.getChildren().add(explosions.get(explosions.size()-1).getView());
                    root.getChildren().remove(bullet.getView());
                    return true;
                }
            }
            return false;
        });
        //remove completed explosions
        explosions.removeIf(explosion->{
            if(explosion.isFinished()){
                root.getChildren().remove(explosion.getView());
                return true;
            }
            return false;
        });
        //check bullet-tank collisions
        for(Bullet bullet:new ArrayList<>(bullets)){
            //player hit by enemy bullet
            if(!bullet.isPlayerBullet()&& bullet.collidesWith(playerTank)){
                bigExplosions.add(new BigExplosion(playerTank.getX(),playerTank.getY()));
                root.getChildren().add(bigExplosions.get(bigExplosions.size()-1).getView());
                handlePlayerHit();
                root.getChildren().remove(bullet.getView());
                bullets.remove(bullet);
                bigExplosions.remove(bigExplosions);
                continue;
            }
            //remove completed big explosions
            bigExplosions.removeIf(bigExplosion ->{
                if(bigExplosion.isFinished()){
                    root.getChildren().remove(bigExplosion.getView());
                    return true;
                }
                return false;
            });
            //check enemy hit by player bullet
            if(bullet.isPlayerBullet()){
                for(EnemyTank enemy :new ArrayList<>(enemyTanks)){
                    if (bullet.collidesWith(enemy)){
                        bigExplosions.add(new BigExplosion(enemy.getX(),enemy.getY()));
                        root.getChildren().add(bigExplosions.get(bigExplosions.size()-1).getView());
                        handleEnemyHit(enemy);
                        root.getChildren().remove(bullet.getView());
                        bullets.remove(bullet);
                        bigExplosions.remove(bigExplosions);
                        break;
                    }
                    //remove completed big explosions
                    bigExplosions.removeIf(bigExplosion ->{
                        if(bigExplosion.isFinished()){
                            root.getChildren().remove(bigExplosion.getView());
                            return true;
                        }
                        return false;
                    });
                }
            }
        }
    }

    //handles player being hit by a bullet
    private void handlePlayerHit(){
        lives--;
        //lives cannot be lower than zero
        lives = Math.max(0, lives);
        livesText.setText("Lives: "+lives);
        if(lives==0){
            gameOver();
        }else{
            //reset to starting position
            playerTank.respawn(400,500);
        }
    }

    //handles enemy being hit by a bullet
    private void handleEnemyHit(EnemyTank enemy){
        score+=100;
        scoreText.setText("Score: "+score);
        enemyTanks.remove(enemy);
        root.getChildren().remove(enemy.getView());
    }

    //shows game over screen with options to restart or exit
    private void gameOver(){
        gameOver = true;
        //create overlay with game over message and options
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(255,255,255,0.5);");
        overlay.setPrefSize(Main.WIDTH, Main.HEIGHT);
        overlay.setId("gameover-overlay");
        Text gameOverText = new Text(Main.WIDTH/2 - 200, Main.HEIGHT/2 - 50, "GAME OVER");
        gameOverText.setStyle("-fx-font-size: 72; -fx-fill: purple;");
        Text finalScoreText = new Text(Main.WIDTH/2 - 140, Main.HEIGHT/2-10, "Your Score: " + score);
        finalScoreText.setStyle("-fx-font-size: 40; -fx-fill: purple;");
        Text optionsText = new Text(Main.WIDTH/2 - 100, Main.HEIGHT/2 + 50, "Press R to Restart\nPress ESC to Exit");
        optionsText.setStyle("-fx-font-size: 18; -fx-fill: purple;");
        overlay.getChildren().addAll(gameOverText,finalScoreText,optionsText);
        root.getChildren().add(overlay);
    }

    //pauses the game and shows pause menu
    private void pauseGame(){
        paused=true;
        //create pause overlay with options
        Pane overlay=new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.7);");
        overlay.setPrefSize(Main.WIDTH, Main.HEIGHT);
        overlay.setId("pause-overlay");
        Text pauseText = new Text(220,300,"GAME PAUSED");
        pauseText.setStyle("-fx-font-size: 64; -fx-fill: white;");
        Text optionsText=new Text(350,350,"Press P to Continue\nPress R to Restart\nPress ESC to Exit");
        optionsText.setStyle("-fx-font-size: 18; -fx-fill: white;");
        overlay.getChildren().addAll(pauseText,optionsText);
        root.getChildren().add(overlay);
    }

    //resumes the game from paused state
    private void resumeGame(){
        paused = false;
        root.getChildren().removeIf(node -> node instanceof Pane && ((Pane)node).getStyle().contains("rgba(0,0,0,0.7)"));
    }

    //resets the game to initial state
    private void restartGame(){
        //clear only non wall elements
        root.getChildren().removeIf(node ->
                (node instanceof ImageView && !walls.stream().anyMatch(wall -> wall.getView() == node)) ||
                        (node instanceof Text && !node.equals(scoreText) && !node.equals(livesText)) ||
                        (node instanceof Pane && (node.getId()!= null&& (node.getId().equals("gameover-overlay") || (node.getId().equals("pause-overlay")))))
        );

        //reset game state
        enemyTanks.clear();
        bullets.clear();
        explosions.clear();
        bigExplosions.clear();
        score = 0;
        lives = 3;
        gameOver = false;
        paused = false;
        scoreText.setText("Score: 0");
        livesText.setText("Lives: 3");

        //recreate player tank
        playerTank = new PlayerTank(400, 500, walls,enemyTanks);
        root.getChildren().add(playerTank.getView());
    }

    //handles key press events for player controls and game commands
    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        //movement keys
        if(code == KeyCode.LEFT) pressedKeys[37]=true;
        else if(code == KeyCode.UP) pressedKeys[38]=true;
        else if(code == KeyCode.RIGHT) pressedKeys[39]=true;
        else if(code == KeyCode.DOWN) pressedKeys[40]=true;

        //action keys
        else if (code==KeyCode.X){//X fire bullet
            Bullet bullet = playerTank.fire();
            bullets.add(bullet);
            root.getChildren().add(bullet.getView());
        }else if (code==KeyCode.P && !gameOver){//P pause
            if(paused) resumeGame();
            else pauseGame();
        }else if (code==KeyCode.R){//R restart
            restartGame();
        }else if (code==KeyCode.ESCAPE){//ESC exit
            System.exit(0);
        }
    }

    //handles key release events for smooth movement
    public void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        if(code == KeyCode.LEFT) pressedKeys[37]=false;
        else if(code == KeyCode.UP) pressedKeys[38]=false;
        else if(code == KeyCode.RIGHT) pressedKeys[39]=false;
        else if(code == KeyCode.DOWN) pressedKeys[40]=false;
    }

    //starts the main game loop
    public void startGame(){
        gameloop.start();
    }
}
