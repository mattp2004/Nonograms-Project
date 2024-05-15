package Nonograms;
import java.io.*;

public class NonogramManager {
    public Nonogram nonogram;

    public static Nonogram createNonogramFromBMP(String _fileName){
        Nonogram nonogram;
        try{
            FileInputStream fileReader = new FileInputStream(_fileName);

            //Reads header information: width
            fileReader.skip(18); 
            byte[] widthBytes = new byte[4];
            fileReader.read(widthBytes);
            int width = byteArrayToInt(widthBytes);

            //Reads header information: height
            byte[] heightBytes = new byte[4];
            fileReader.read(heightBytes);
            int height = byteArrayToInt(heightBytes);

            //Reads header information: bits per pixel
            fileReader.skip(2);
            byte[] bppBytes = new byte[2];
            fileReader.read(bppBytes);
            int bpp = byteArrayToInt(bppBytes);  

            //Reads the location of where the pixel data is stored
            fileReader.getChannel().position(0);
            fileReader.skip(10);
            byte[] pixelDataOffsetBinary = new byte[4];
            fileReader.read(pixelDataOffsetBinary);
            int pixelDataOffset = byteArrayToInt(pixelDataOffsetBinary); 

            double bytesPerPixel = (double) bpp / 8; 
            
            //Real bytes per row
            int bytesPerRow = (int) Math.ceil(width * bpp / 8.0);
            //Total bytes including padding by rounding up to nearest multiple of 4
            int totalBytesRow = (int) Math.ceil((width * bytesPerPixel) / 4.0) * 4; 
            //Total bytes overall including filler (whole image)
            int totalBytes = totalBytesRow * height; 

            //DEBUG
            System.out.println("[DEBUG] bitsPerPixel: "+ bpp+ ", Width x Height: " + width +"x" + height + ", TotalBytes: " + totalBytes);

            PixelValue[][] pixels = new PixelValue[height][width];
            fileReader.getChannel().position(pixelDataOffset);
            //DEBUG
            int count = 0;
            System.out.print("[DEBUG] Raw bits: "); 

            //Iterates through each column
            for(int y = height -1; y >= 0; y--){         
                byte[] currentBytes = new byte[totalBytesRow];
                //Reads only the amount of bytes needed for the row (including padding)
                fileReader.read(currentBytes); 

                int currentByte=0;
                int currentBit=7;

                //Interates through each row
                for(int x =0; x < width; x++){
                    int[] values = new int[bpp];
                    //Iterates through each bit per pixel
                    for(int i = 0; i < bpp; i++){
                        //If reach last bit in byte, move onto the next byte.
                        if(currentBit < 0){
                            currentByte +=1;
                            currentBit = 7;
                            // This should never be true but after 5 hours of painful testing it somehow can be.
                            if(currentByte > bytesPerRow){ 
                                System.out.println("something has gone terribly wrong");
                            }
                        }
                        //Isolate the specific bit (currentBit)
                        int bit = (currentBytes[currentByte] >> currentBit) &1; 
                        values[i] = bit;
                        currentBit -=1;
                        count +=1; //Debug
                    }
                    //Debug
                    for(int i = 0; i < values.length; i++){ 
                        System.err.print(values[i]);
                    }

                    
                    pixels[y][x] = new PixelValue(bpp);
                    pixels[y][x].values = values;

                }
            }
            //Debug
            System.out.println();
            System.out.println("[DEBUG] Bits CheckSum: "+count + "/" + bpp*width*height); //Debug

            //Closes file reader
            fileReader.close();

            nonogram = new Nonogram(width,height,pixels,bpp);
            return nonogram;

        }
        catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
    }

    //Removes signed bit ensuring that the byte can be converted to an unsigned integer.
    public static int byteToInt(byte _byte){
        return _byte & 0xff; 
    }

    public static int byteArrayToInt(byte[] _bytes){
        int result = 0;
        for(int i = 0; i < _bytes.length; i++){
            result += byteToInt(_bytes[i]) * Math.pow(256, i);
        }
        return result;
    }
}