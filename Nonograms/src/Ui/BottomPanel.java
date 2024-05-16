package Ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BottomPanel extends JPanel {
    GameWindow window;
    public BottomPanel(GameWindow _window){
        this.window = _window;

        //Creates a new button to check if the puzzle is correct, it calls the appropriate function to check.
        JButton checkButton = new JButton("Check");
        checkButton.setPreferredSize(new Dimension(120,60));
        checkButton.addActionListener(e ->window.gridPanel.checkCompleted());
        this.add(checkButton, BorderLayout.CENTER);
    }
}
