package Ui;

import javax.swing.*;

import Nonograms.Nonogram;

public class GameWindow extends JFrame {
    Nonogram nonogram;
    GridPanel gridPanel;

    public GameWindow(Nonogram nonogram){
        //Sets the properties of the window
        this.setTitle("Hanjie Puzzle ");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adds the grid panel as a new component of the Jframe
        gridPanel = new GridPanel(nonogram);
        this.add(gridPanel);

        //Sets the window to be visible after all tasks have been completed. 
        this.setVisible(true);
        this.setResizable(true);
    }
}
