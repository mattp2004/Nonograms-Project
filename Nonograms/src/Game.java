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
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
        }        

        nonogram = NonogramManager.createNonogramFromBMP(filePath);
        gameWindow = new GameWindow(nonogram);
        
    }
    public static Game getInstance(){
        return instance;
    }
}
