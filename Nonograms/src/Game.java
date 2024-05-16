import javax.swing.JFileChooser;

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
        nonogram = NonogramManager.createNonogramFromBMP("2colour_elephant.bmp");
        gameWindow = new GameWindow(nonogram);
    }

    public static Game getInstance(){
        return instance;
    }
}
