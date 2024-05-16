package Ui;

import java.awt.BorderLayout;
import javax.swing.*;

import Nonograms.Nonogram;

public class GameWindow extends JFrame {
    Nonogram nonogram;
    GridPanel gridPanel;
    BottomPanel bottomPanel;

    public GameWindow(Nonogram nonogram){
        //Sets the properties of the window
        this.setTitle("Hanjie Puzzle ");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adds the grid panel as a new component of the Jframe
        gridPanel = new GridPanel(nonogram);
        this.add(gridPanel, BorderLayout.CENTER);

        //Adds the bottom panel as a new component of the Jframe
        bottomPanel = new BottomPanel(this);
        this.add(bottomPanel, BorderLayout.SOUTH);

        //Sets the window to be visible after all tasks have been completed. 
        this.setVisible(true);
        this.setResizable(true);
    }
}
