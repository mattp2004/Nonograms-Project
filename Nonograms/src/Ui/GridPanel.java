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
import Nonograms.NonogramManager;

public class GridPanel extends JPanel{

    Nonogram nonogram;
    JButton[][] buttons;
    Color[] colours; 

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

    private Color getColour(int[] values){
        Color colour;

        //Sets up RGB  values
        int red = 0;
        int green = 0;
        int blue = 0;

        //If bpp = 1 then all RGB values * 255 = white
        if(nonogram.bpp == 1){
            red = values[0] * 255;
            green = values[0] * 255;
            blue = values[0] * 255;
        }
        //Support for 24bpp
        else if(nonogram.bpp == 24){
            //Iterates through the first 8 bits and concatenates them together to make a byte
            //Converts all of the bits into 3 bytes 
            byte blueByte = 0;
            for(int i = 0; i < 8; i++){
                blueByte += (values[i] << (7-i));
            }

            byte greenByte = 0;
            for (int i = 8; i < 16; i++) {
                greenByte |= (values[i] << (15 - i));
            }
            
            byte redByte = 0;
            for (int i = 16; i < 24; i++) {
                redByte |= (values[i] << (23 - i));
            }

            //Converts the bytes to an unsigned integer where they can be used to create a colour from their RGB value.
            red = NonogramManager.byteToInt(redByte);
            green = NonogramManager.byteToInt(greenByte);
            blue = NonogramManager.byteToInt(blueByte);
        }

        //Creates and returns the new colour
        colour = new Color(red,green,blue);
        return colour;
    }

    private void createGrid(JPanel grid) {
        //Iterates through the height and the width of the image 
        for (int y = 0; y < nonogram.height; y++) {
            for (int x = 0; x < nonogram.width; x++) {
                JButton button = new JButton();
                //Sets colour to white if first bit of the pixel's data starts with a 1 (for testing)
                Color pixelColour = getColour(nonogram.pixelValues[y][x].values);
                button.setBackground(pixelColour);
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
