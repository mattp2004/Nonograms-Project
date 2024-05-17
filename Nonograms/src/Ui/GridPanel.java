package Ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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

        grid.setBorder(new EmptyBorder(0,0,0,40));

        setLayout(new BorderLayout());

        //Create a new panel which will store all of the column number hints
        JPanel colNumbersPanel = new JPanel(new GridLayout(1,nonogram.width));
        for(int x = 0; x < nonogram.width; x++){
            colNumbersPanel.add(getColNumberHint(x));
        }

        //Populates the panel with number hints for each row.
        JPanel rowNumbersPanel = new JPanel(new GridLayout(nonogram.height, 1));
        for(int x = 0; x < nonogram.height; x++){
            rowNumbersPanel.add(getRowNumberHint(x));
        }

        //Sets border to align numbers based on width of nonogram
        colNumbersPanel.setBorder(new EmptyBorder(0,17*nonogram.width,0,2*nonogram.width));
        rowNumbersPanel.setBorder(new EmptyBorder(0,30,0,0));

        //Adds the components to the panel.
        add(colNumbersPanel, BorderLayout.NORTH);
        add(rowNumbersPanel, BorderLayout.WEST);
        add(grid, BorderLayout.CENTER);

        //Creates the grid
        createGrid(grid);
    }
    
    private JPanel getRowNumberHint(int y) {
        //Creates new panel and assigns flow layout so that all the numbers are added in a horizontal line going right.
        JPanel colNumbers = new JPanel();
        colNumbers.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));

        //
        int consecutivePixels = 0;
        Color previousColour = getColour(nonogram.pixelValues[y][0].values);

        //Iterates through all of the rows.
        for(int x = 0; x < nonogram.width; x++){
            Color pixelColour = getColour(nonogram.pixelValues[y][x].values);
            //If the pixel colour is the same as the previous, increment count
            if(pixelColour.equals(previousColour)){
                consecutivePixels +=1;
            }
            //If they are not the chain of pixels has ended.
            else{
                //If pixel is not blank then add the label of consecutive pixels
                if(!previousColour.equals(Color.WHITE)){
                    JLabel label = new JLabel(consecutivePixels +  ",      ");
                    label.setForeground(previousColour);
                    colNumbers.add(label);
                }
                consecutivePixels = 1;
                previousColour = pixelColour;
            }
    
        }
        //Adds the consecutive pixels that are not subsequently followed by any others.
        if(!previousColour.equals(Color.WHITE)){
            if(consecutivePixels > 0){
                JLabel label = new JLabel(consecutivePixels + "       ");
                label.setForeground(previousColour);
                colNumbers.add(label);
            }
        }
        
        return colNumbers;
    }

    private JPanel getColNumberHint(int x) {
        //Creates new panel and assigns BoxLayout on the Y axis so the numbers are displayed vertically.
        JPanel colNumbers = new JPanel();
        colNumbers.setLayout(new BoxLayout(colNumbers, BoxLayout.Y_AXIS));
        //Used to avoid floating numbers
        colNumbers.add(Box.createVerticalGlue());

        int consecutivePixels = 0;
        Color previousColour = getColour(nonogram.pixelValues[0][x].values);

        //Iterates through all of the columns.
        for(int y = 0; y < nonogram.height; y++){
            Color pixelColour = getColour(nonogram.pixelValues[y][x].values);
            //If the pixel colour is the same as the previous, increment count
            if(pixelColour.equals(previousColour)){
                consecutivePixels +=1;
            }
            //If they are not the chain of pixels has ended.
            else{
                //If pixel is not blank then add the label of consecutive pixels
                if(!previousColour.equals(Color.WHITE)){
                    JLabel label = new JLabel(consecutivePixels +  ", ");
                    label.setForeground(previousColour);
                    colNumbers.add(label);
                }
                consecutivePixels = 1;
                previousColour = pixelColour;
            }
    
        }
        //Adds the consecutive pixels that are not subsequently followed by any others.
        if(!previousColour.equals(Color.WHITE)){
            if(consecutivePixels > 0){
                JLabel label = new JLabel(consecutivePixels + " ");
                label.setForeground(previousColour);                
                colNumbers.add(label);
            }
        }
        return colNumbers;
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
                button.setPreferredSize(new Dimension(15, 15));
                
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
