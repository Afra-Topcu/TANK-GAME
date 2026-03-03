import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Explosion {
    private ImageView view;
    private long startTime;
    private static final long DURATION=500_000_000L; //0.5 second

    public Explosion(double x,double y){
        view = new ImageView(new Image("file:assets/smallExplosion.png"));
        view.setX(x);
        view.setY(y);
        view.setFitWidth(20);
        view.setFitHeight(20);
        //record the creation time for animation timing
        startTime = System.nanoTime();
    }
    //gets the visual representation of this explosion
    public ImageView getView(){
        return view;
    }

    //checks if the explosion animation has completed
    public boolean isFinished(){
        //compare current time with creation time+duration
        return System.nanoTime()-startTime>DURATION;
    }
}
