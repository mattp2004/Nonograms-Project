package Nonograms;
import java.io.*;

public class NonogramManager {
    public Nonogram nonogram;

    public static void createNonogramFromBMP(){
        try{
            FileInputStream fileReader = new FileInputStream("elephant.bmp");

            byte[] headerSizeBytes = new byte[4];
            fileReader.skip(14); //header size
            fileReader.read(headerSizeBytes, 0, 4);

            int headerSize = byteArrayToInt(headerSizeBytes);
            System.err.println(headerSize);
        }
        catch(IOException exception){
            exception.printStackTrace();
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
