import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet implements GameInterface {
    private ImageView view;
    private double x,y;
    private double speed=5.0;
    private double direction;//direction in degrees
    //dimensions of the bullet
    private double width=10;
    private double height=10;
    //flat indicating if bullet was fired by player(true) or enemy(false)
    private boolean isPlayerBullet;

    public Bullet(double x, double y,double direction,boolean isPlayerBullet) {
        this.x=x;
        this.y=y;
        this.direction=direction;
        this.isPlayerBullet=isPlayerBullet;
        //initialize bullet graphics
        view=new ImageView(new Image("file:assets/bullet.png"));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
        view.setRotate(direction);
    }
    public void move(){
        switch ((int)direction){
            case 270:y-=speed;break;//move up
            case 0:x+=speed;break;//move right
            case 90:y+=speed;break;//move down
            case 180:x-=speed;break;//move left
        }
        //update visual position
        view.setX(x);
        view.setY(y);
    }
    //check if bullet collides with any other object in the game
    public boolean collidesWith(GameInterface obj){
        return x<obj.getX()+obj.getWidth()&&
                x+width>obj.getX() &&
                y<obj.getY()+obj.getHeight()&&
                y+height>obj.getY();
    }
    //check if bullet was fired by player
    public boolean isPlayerBullet() {
        return isPlayerBullet;
    }

    //gameinterface implementation
    public ImageView getView() {
        return view;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }


}
