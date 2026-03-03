import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

public class EnemyTank implements GameInterface{
    private double x,y;
    private double width=35;
    private double height=35;
    private double speed=2.0;
    private ImageView view;
    private List<Walls>walls;
    private PlayerTank playerTank;
    private List<EnemyTank> otherEnemies;
    private int currentDirection;
    private Random random=new Random();

    public EnemyTank(double x,double y,List<Walls> walls,PlayerTank playerTank,List<EnemyTank> otherEnemies) {
        this.x = x;
        this.y = y;
        this.walls = walls;
        this.playerTank = playerTank;
        this.otherEnemies = otherEnemies;
        this.currentDirection = 180; //start facing downward

        //initialize enemy tank graphics
        view = new ImageView(new Image("file:assets/whiteTank1.png"));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
        view.setRotate(currentDirection);
    }
    //check if its collide with other tanks
    private boolean collidesWithOtherTanks(double newX, double newY) {
        //check if its collides with player tank
        if (newX < playerTank.getX() + playerTank.getWidth() &&
                newX + width > playerTank.getX() &&
                newY < playerTank.getY() + playerTank.getHeight() &&
                newY + height > playerTank.getY()) {
            return true;
        }
        //check if its collides with other enemy tanks
        for (EnemyTank enemy : otherEnemies) {
            if (enemy != this &&
                    newX < enemy.getX() + enemy.getWidth() &&
                    newX + width > enemy.getX() &&
                    newY < enemy.getY() + enemy.getHeight() &&
                    newY + height > enemy.getY()) {
                return true;
            }
        }
        return false;
    }

    //moves tank in its current direction
    public void move() {
        switch (currentDirection) {
            case 0:   moveUp();    break;
            case 90:  moveRight(); break;
            case 180: moveDown();  break;
            case 270: moveLeft();   break;
        }
    }
    //randomly change direction
    public void changeDirection() {
        currentDirection = random.nextInt(4)*90;
        view.setRotate(currentDirection);
    }

    //check if enemy tank collides with wall
    private boolean collidesWithWalls(double newX,double newY){
        for(Walls wall:walls){
            if(newX<wall.getX()+wall.getWidth()&&
            newX+width>wall.getX()&&
            newY<wall.getY()+wall.getHeight()&&
            newY+height>wall.getY()){
                return true;
            }
        }
        return false;
    }
    //get visual representation
    public ImageView getView(){
        return view;
    }
    //moves tank left if no collisions detected
    public void moveLeft(){
        double newX=Math.max(50, x-speed);
        if(!collidesWithWalls(newX,y)&&!collidesWithOtherTanks(newX,y)){
            x=newX;
            view.setX(x);
            view.setRotate(180);//face left
        }
    }
    //moves tank right if no collisions detected
    public void moveRight(){
        double newX=Math.min(Main.WIDTH - 50 - width, x + speed);
        if(!collidesWithWalls(newX,y)&&!collidesWithOtherTanks(newX,y)){
            x=newX;
            view.setX(x);
            view.setRotate(0);//face right
        }
    }
    //moves tank up if no collisions detected
    public void moveUp(){
        double newY=Math.max(50, y-speed);
        if(!collidesWithWalls(x,newY)&&!collidesWithOtherTanks(x,newY)){
            y=newY;
            view.setY(y);
            view.setRotate(270);//face up
        }
    }
    //moves tank down if no collisions detected
    public void moveDown(){
        double newY=Math.min(Main.HEIGHT - 50 - height, y + speed);
        if(!collidesWithWalls(x,newY)&&!collidesWithOtherTanks(x,newY)){
            y=newY;
            view.setY(y);
            view.setRotate(90);//face down
        }
    }
    //creates a new bullet fired from this tank
    public Bullet fire(){
        //calculate bullet starting position (center of tank)
        double bulletX=x+width/2-5;
        double bulletY=y+height/2-5;
        return new Bullet(bulletX,bulletY,view.getRotate(),false);
    }
    //gameinterface implementation
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
}
