import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BigExplosion {
    private ImageView view;
    private long startTime;
    private static final long DURATION=1_000_000_000L;

    public BigExplosion(double x, double y){
        //load image
        view = new ImageView(new Image("file:assets/explosion.png"));
        view.setX(x-20);
        view.setY(y-20);
        //set dimensions of the explosion
        view.setFitHeight(40);
        view.setFitWidth(40);
        //record the creation time for animation timing
        startTime = System.nanoTime();
    }
    //gets imageview containing the explosion visual
    public ImageView getView(){
        return view;
    }
    public boolean isFinished(){
        //compare current time with creation time duration
        return System.nanoTime()-startTime>DURATION;
    }
}
