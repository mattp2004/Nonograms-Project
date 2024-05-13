package Nonograms;

public class PixelValue {

    public int[] values;

    public PixelValue(int _bpp){
        int size = (int) Math.ceil(_bpp / 8.0);
        values = new int[size];
    }
    
}
