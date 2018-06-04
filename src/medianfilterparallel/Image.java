/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medianfilterparallel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author rowanvi
 */
public class Image {

    private String input;
    private String output;
    private String extension;
    private BufferedImage image = null;
    private BufferedImage originalImage;
    private int count = 0;
    private int modulo = 0;
    
      public Image(String input, String output, String extension) {
        this.input = input;
        this.output = output;
        this.extension = extension;
    }
    
      public BufferedImage getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }
    
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    

    public synchronized void applyMedian(int offsetHeight, int height, int width) throws IOException, Exception {
        Color[] surroundedPixel = new Color[9];
        int[] R = new int[9];
        int[] B = new int[9];
        int[] G = new int[9];
        
        if(offsetHeight != 0){
            offsetHeight = offsetHeight - 1;
        }else if(offsetHeight == 0){
            offsetHeight = offsetHeight + 1;
        }
        for (int j = offsetHeight ; j < offsetHeight + height; j++) {
            for (int i = 1; i < this.image.getWidth() - 1; i++) {
                surroundedPixel[0] = new Color(this.originalImage.getRGB(i - 1, j - 1));
                surroundedPixel[1] = new Color(this.originalImage.getRGB(i - 1, j));
                surroundedPixel[2] = new Color(this.originalImage.getRGB(i - 1, j + 1));
                surroundedPixel[3] = new Color(this.originalImage.getRGB(i, j + 1));
                surroundedPixel[4] = new Color(this.originalImage.getRGB(i + 1, j + 1));
                surroundedPixel[5] = new Color(this.originalImage.getRGB(i + 1, j));
                surroundedPixel[6] = new Color(this.originalImage.getRGB(i + 1, j - 1));
                surroundedPixel[7] = new Color(this.originalImage.getRGB(i, j - 1));
                surroundedPixel[8] = new Color(this.originalImage.getRGB(i, j));
                for (int k = 0; k < 9; k++) {
                    R[k] = surroundedPixel[k].getRed();
                    B[k] = surroundedPixel[k].getBlue();
                    G[k] = surroundedPixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                this.image.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
            }
        }
//        if (this.modulo > 0 && threadAmount == (threadCounter + 1)) {
//            for (int j = (heightPerThread * threadAmount); j < (heightPerThread * threadAmount) + modulo - 2; j++) {
//                for (int i = 1; i < bufferedImage.getWidth() - 1; i++) {
//                    surroundedPixel[0] = new Color(bufferedImage.getRGB(i - 1, j - 1));
//                    surroundedPixel[1] = new Color(bufferedImage.getRGB(i - 1, j));
//                    surroundedPixel[2] = new Color(bufferedImage.getRGB(i - 1, j + 1));
//                    surroundedPixel[3] = new Color(bufferedImage.getRGB(i, j + 1));
//                    surroundedPixel[4] = new Color(bufferedImage.getRGB(i + 1, j + 1));
//                    surroundedPixel[5] = new Color(bufferedImage.getRGB(i + 1, j));
//                    surroundedPixel[6] = new Color(bufferedImage.getRGB(i + 1, j - 1));
//                    surroundedPixel[7] = new Color(bufferedImage.getRGB(i, j - 1));
//                    surroundedPixel[8] = new Color(bufferedImage.getRGB(i, j));
//                    for (int k = 0; k < 9; k++) {
//                        R[k] = surroundedPixel[k].getRed();
//                        B[k] = surroundedPixel[k].getBlue();
//                        G[k] = surroundedPixel[k].getGreen();
//                    }
//                    Arrays.sort(R);
//                    Arrays.sort(G);
//                    Arrays.sort(B);
//                    this.image.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
//                }
//            }
//        }
    }

    public void createImage() {
        try {
            ImageIO.write(this.image, this.getExtension(), new File(this.output));
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
