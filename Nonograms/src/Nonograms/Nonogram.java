package Nonograms;

public class Nonogram {
    public int width;
    public int height;
    public int bpp;
    public PixelValue[][] pixelValues;

    public Nonogram(int _width, int _height, PixelValue[][] _values, int _bpp){
        width = _width;
        height = _height;
        pixelValues = _values;
        bpp = _bpp;
    }
}
