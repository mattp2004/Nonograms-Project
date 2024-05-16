package Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Nonograms.Nonogram;
import Nonograms.NonogramManager;

public class GridPanel extends JPanel{

    Nonogram nonogram;
    JButton[][] buttons;
    ArrayList<Color> colours;
    Color[][] completedPuzzle;
    Boolean toReset;
    JPanel grid;

    //Creates a border to apply to the buttons
    Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
    Border incorrectLineBorder = BorderFactory.createLineBorder(Color.RED);

    public GridPanel(Nonogram _nonogram){
        //Instantiates variables
        toReset = false;
        this.nonogram = _nonogram;
        colours = new ArrayList<Color>();

        completedPuzzle = new Color[_nonogram.height][nonogram.width];

        buttons = new JButton[nonogram.height][nonogram.width];
        grid = new JPanel(new GridLayout(nonogram.height, nonogram.width));

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

        //If the new colour is not in the list of colours it will add it
        if(!colours.contains(colour)){
            colours.add(colour);
        }
        return colour;
    }

    private void createGrid(JPanel grid) {
        //Iterates through the height and the width of the image 
        for (int y = 0; y < nonogram.height; y++) {
            for (int x = 0; x < nonogram.width; x++) {
                JButton button = new JButton();
                //Sets colour to white if first bit of the pixel's data starts with a 1 (for testing)
                Color pixelColour = getColour(nonogram.pixelValues[y][x].values);
                completedPuzzle[y][x] = pixelColour;
                
                button.setBackground(Color.WHITE);

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
        if(toReset){
            return;
        }
        //Gets the current colour
        Color currentColor = button.getBackground();
        int colourIndex=0;

        //Finds the index of the current colour 
        for(int i = 0; i < colours.size(); i++){
            if(colours.get(i) == currentColor){
                colourIndex = i;
            }
        }
        //Incremented colour index and if reached the max it resets to 0
        colourIndex +=1;
        if(colourIndex == colours.size()){
            colourIndex = 0;
        }
        //Sets the new colour
        button.setBackground(colours.get(colourIndex));
        buttons[y][x] = button;
    }

    public void resetPuzzle(){
        toReset = false;
        //Resets the colours of the buttons as well as the border colour.
        for (int y = 0; y < nonogram.height; y++) {
            for (int x = 0; x < nonogram.width; x++) {
                buttons[y][x].setBackground(Color.WHITE); 
                buttons[y][x].setBorder(lineBorder);
            }
        }
    }

    //Checks to see if the puzzle is correct
    public Boolean checkCompleted() {
        //Invalidates check if puzzle has already been checked and needs to be reset.
        if(toReset){
            JOptionPane.showMessageDialog(this, "Press reset to retry.");
            return false;
        }
        Boolean completed = true;
        int correctCells = 0;
        int blankCells = 0;
        //Iterates through each pixel and checks if it's colour is as expected.
        for (int y = 0; y < nonogram.height; y++) {
            for (int x = 0; x < nonogram.width; x++) {
                Color buttonColour = buttons[y][x].getBackground();
                Color expectedColour = completedPuzzle[y][x];
                //if it is not as expected it sets the puzzle to be not complete and highlights the invalid pixels.
                if (!buttonColour.equals(expectedColour)) {
                    completed = false;
                    buttons[y][x].setBorder(incorrectLineBorder);
                    buttons[y][x].setBackground(expectedColour);
                }  
                else{
                    //Calculates the number of correct cells out of the number of real cells (excluding blank cells)
                    if(buttonColour != Color.WHITE){
                        correctCells +=1;
                    }
                    else{
                        blankCells +=1;
                    }
                }
            }
        }
        //Sets the puzzle to have to be reset.
        toReset = true;

        //Sends the result of the game message to the user.
        int totalCells = (nonogram.width*nonogram.height) - blankCells;
        if(completed){
            JOptionPane.showMessageDialog(this, "Congrats you got "+ correctCells + "/" + totalCells + ". The puzzle is correct.");
        }
        else{
            JOptionPane.showMessageDialog(this, "Incorrect. There are "+ correctCells + "/" + totalCells + " correctly marked cells. Try again.");

        }
        return completed;
    }
    
}
