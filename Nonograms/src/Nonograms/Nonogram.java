package Nonograms;

public class Nonogram {

    public int width;
    public int height;
    public PixelValue[][] pixelValues;

    public Nonogram(int _width, int _height, PixelValue[][] _values){
        width = _width;
        height = _height;
        pixelValues = _values;
    }
}
