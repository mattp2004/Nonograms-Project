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
        String filePath = "";
        //Opens file explorer to select a bmp file to load
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            //Get path of file selected
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
        }        

        //Load path
        nonogram = NonogramManager.createNonogramFromBMP(filePath);
        gameWindow = new GameWindow(nonogram);
    }
    public static Game getInstance(){
        return instance;
    }
}
