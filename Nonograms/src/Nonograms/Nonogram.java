package Nonograms;

public class Nonogram {

    public int width;
    public int height;
    public PixelValue[][] pixelValues;

    public Nonogram(){
        pixelValues = new PixelValue[width][height];
    }
}
