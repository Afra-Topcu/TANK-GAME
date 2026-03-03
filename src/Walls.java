import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Walls  implements GameInterface{
    private double x,y;
    private double width=30;
    private double height=30;
    private ImageView view;

    public Walls(double x,double y) {
        this.x=x;
        this.y=y;
        //initialize walls graphics
        view=new ImageView(new Image("file:assets/wall.png"));
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setX(x);
        view.setY(y);
    }

    //returns the visual representation of the wall
    public ImageView getView() {
        return view;
    }

    //gameinterface implementations
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
