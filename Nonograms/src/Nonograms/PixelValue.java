package Nonograms;

public class PixelValue {

    int[] values;
    int bpp;

    public PixelValue(int _bpp, int[] _values){
        bpp = _bpp;
        values = new int[bpp];
        values = _values;
    }
    
}
