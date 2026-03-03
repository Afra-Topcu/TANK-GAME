import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

class PlayerTank implements GameInterface{
    private double x,y;
    private double speed=3.0;
    private double width=35;
    private double height=35;
    private ImageView view;
    private List<Walls>walls;
    private List<EnemyTank> enemyTanks;

    public PlayerTank(double x,double y,List<Walls> walls,List<EnemyTank> enemyTanks){
        this.x=x;
        this.y=y;
        this.walls=walls;
        this.enemyTanks=enemyTanks;
        //initialize tank image
        view = new ImageView(new Image("file:assets/yellowTank1.png"));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
    }

    //checks for collision with enemy tanks
    private boolean collidesWithTanks(double newX, double newY) {
        for (EnemyTank enemy : enemyTanks) {
            if (newX < enemy.getX() + enemy.getWidth() &&
                    newX + width > enemy.getX() &&
                    newY < enemy.getY() + enemy.getHeight() &&
                    newY + height > enemy.getY()) {
                return true;
            }
        }
        return false;
    }

    //checks for collision with walls
    private boolean collidesWithWalls(double newX,double newY){
        for (Walls wall : walls){
            if(newX<wall.getX() + wall.getWidth()&&
            newX+width>wall.getX()&&
            newY<wall.getY()+wall.getHeight()&&
            newY+height>wall.getY()){
                return true;
            }
        }
        return false;
    }

    //returns the visual representation of the tank
    public ImageView getView(){
        return view;
    }

    //moves tank left if no collisions detected
    public void moveLeft(){
        double newX=Math.max(50,x-speed);
        if(!collidesWithWalls(newX,y)&& !collidesWithTanks(newX,y)){
            x=newX;
            view.setX(x);
            view.setRotate(180);
        }
    }
    //moves tank right if no collisions detected
    public void moveRight(){
        double newX=Math.min(Main.WIDTH-50-width,x+speed);
        if(!collidesWithWalls(newX,y)&& !collidesWithTanks(newX,y)){
            x=newX;
            view.setX(x);
            view.setRotate(0);
        }
    }
    //moves tank up if no collisions detected
    public void moveUp(){
        double newY=Math.max(50,y-speed);
        if(!collidesWithWalls(x,newY)&& !collidesWithTanks(x,newY)){
            y=newY;
            view.setY(y);
            view.setRotate(270);
        }
    }
    //moves tank down if no collisions detected
    public void moveDown(){
        double newY=Math.min(Main.HEIGHT-50-height,y+speed);
        if(!collidesWithWalls(x,newY)&& !collidesWithTanks(x,newY)){
            y=newY;
            view.setY(y);
            view.setRotate(90);
        }
    }

    //fires a bullet from the tank's current position and direction
    public Bullet fire(){
        //create bullet in front of the tank
        double bulletX=x+width/2-5;
        double bulletY=y+height/2-5;
        return new Bullet(bulletX,bulletY,view.getRotate(),true);
    }

    //respawn the tank at the specified position
    public void respawn(double x,double y){
        this.x=x;
        this.y=y;
        view.setX(x);
        view.setY(y);
        view.setRotate(0); //default direction
    }

    //gameinterface implementations
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
