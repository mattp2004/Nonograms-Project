package Nonograms;

public class PixelValue {

    int[] values;
    int bpp;
    
    public PixelValue(int _bpp){
        bpp = _bpp;
        values = new int[bpp];
    }
    
}
