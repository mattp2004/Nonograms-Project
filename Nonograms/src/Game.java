import Nonograms.Nonogram;
import Nonograms.NonogramManager;
import Ui.GameWindow;

public class Game {
    public static Game instance;
    
    public Nonogram nonogram;
    public GameWindow gameWindow;

    public Game(){
        instance = this;
    }

    public void setup(){
        //2colour elephant = 24 bpp
        nonogram = NonogramManager.createNonogramFromBMP("3colour_basketball.bmp");
        gameWindow = new GameWindow(nonogram);
    }

    public static Game getInstance(){
        return instance;
    }
}
