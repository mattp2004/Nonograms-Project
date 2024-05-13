import Nonograms.Nonogram;
import Nonograms.NonogramManager;

public class Game {
    public static Game instance;
    
    public Nonogram nonogram;

    public Game(){
        instance = this;
    }

    public void setup(){
        NonogramManager.createNonogramFromBMP("elephant.bmp");
    }

    public static Game getInstance(){
        return instance;
    }
}
