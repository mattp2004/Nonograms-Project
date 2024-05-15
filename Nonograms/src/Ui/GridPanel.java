package Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;


import Nonograms.Nonogram;

public class GridPanel extends JPanel{

    Nonogram nonogram;
    JButton[][] buttons;
    
    //Creates a border to apply to the buttons
    Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);

    public GridPanel(Nonogram _nonogram){
        //Instantiates 
        this.nonogram = _nonogram;
        buttons = new JButton[nonogram.height][nonogram.width];
        JPanel grid = new JPanel(new GridLayout(nonogram.height, nonogram.width));

        createGrid(grid);
        add(grid, BorderLayout.CENTER);
    }

    private void createGrid(JPanel grid) {
        //Iterates through the height and the width of the image 
        for (int y = 0; y < nonogram.height; y++) {
            for (int x = 0; x < nonogram.width; x++) {
                JButton button = new JButton();
                //Sets colour to white if first bit of the pixel's data starts with a 1 (for testing)
                if(nonogram.pixelValues[y][x].values[0] == 1){
                    button.setBackground(Color.white);
                }
                else{
                    button.setBackground(Color.black);
                }
                //Applies the border to the button
                button.setBorder(lineBorder);
                button.setOpaque(true);
                button.setBorderPainted(true);

                //has to be made final to call listener function. 
                final int _y = y;
                final int _x = x;

                //Calls the clicked function when the listener is activated.
                button.addActionListener(e -> pixelClick(button, _y, _x));
                button.setPreferredSize(new Dimension(25, 25));
                
                //Assigns and adds the button 
                buttons[y][x] = button;
                grid.add(button);
            }
        }
    }

    //Runs everytime a pixel(button) is clicked
    private void pixelClick(JButton button, int y, int x){
        //To be replaced with proper functionality for interpreting colour. 
        Color currentColor = button.getBackground();
        //Switches pixel between black and white for testing purposes.
        if(currentColor == Color.black){
            button.setBackground(Color.white);
        }
        else{
            button.setBackground(Color.black);
        }
    }
    
}
