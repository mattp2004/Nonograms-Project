package Nonograms;
import java.io.*;
import java.util.Arrays;

public class NonogramManager {
    public Nonogram nonogram;

    public static Nonogram createNonogramFromBMP(){
        Nonogram nonogram;
        try{
            FileInputStream fileReader = new FileInputStream("elephant.bmp");

            byte[] headerSizeBytes = new byte[4];
            fileReader.skip(14); //header size stored 14-17
            fileReader.read(headerSizeBytes);
            int headerSize = byteArrayToInt(headerSizeBytes);

            System.err.println("Header size is " + headerSize);

            byte[] widthBytes = new byte[4];
            fileReader.read(widthBytes);
            int width = byteArrayToInt(widthBytes);
            System.err.println("Width is " + width);

            byte[] heightBytes = new byte[4];
            fileReader.read(heightBytes);
            int height = byteArrayToInt(heightBytes);
            System.err.println("Height is " + height); // Pointer is at 26

            fileReader.skip(2);
            byte[] bppBytes = new byte[2];
            fileReader.read(bppBytes);
            int bpp = byteArrayToInt(bppBytes);  
            System.err.println("Bits per pixel " + bpp); // Pointer is at 26

            fileReader.getChannel().position(0);
            fileReader.skip(10);
            byte[] pixelDataOffsetBinary = new byte[4];
            fileReader.read(pixelDataOffsetBinary);
            int pixelDataOffset = byteArrayToInt(pixelDataOffsetBinary);
            System.err.println("Pixel offset " + pixelDataOffset); // 14

            fileReader.getChannel().position(pixelDataOffset);

            for(int row = width-1; row >= 0; row--){
                System.err.println(row);
            }

            nonogram = new Nonogram();
            // for(int i = 0; i < width; i++){
            //     for(int z = 0; z < height; z++){
            //         int[] value = {1};
            //         nonogram.pixelValues[i][z] = new PixelValue(bpp, value);
            //     }
            // }
            return nonogram;
        }
        catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public static int byteToInt(byte _byte){
        return _byte >>> 0; 
    }

    public static int byteArrayToInt(byte[] _bytes){
        int result = 0;
        for(int i = 0; i < _bytes.length; i++){
            result += byteToInt(_bytes[i]) * Math.pow(256, i);
        }
        return result;
    }
}
