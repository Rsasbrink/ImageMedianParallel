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
    private int count = 0;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Image(String input, String output, String extension) {
        this.input = input;
        this.output = output;
        this.extension = extension;
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
    
    public void applyMedian(int threadAmount) throws IOException, Exception {
        File imageFile = new File(this.input);
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int threadCounter = this.getCount();
        this.setCount(this.count + 1);
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        if(this.image == null){
            this.image = new BufferedImage(imageWidth, imageHeight, bufferedImage.getType());
        }
        int heightPerThread = imageHeight / threadAmount;
        Color[] surroundedPixel = new Color[9];
        int[] R = new int[9];
        int[] B = new int[9];
        int[] G = new int[9];
        for (int j = 1 + (heightPerThread * threadCounter); j < (heightPerThread * (threadCounter + 1)) - 1; j++) {
            for (int i = 1; i < bufferedImage.getWidth() - 1; i++) {
                surroundedPixel[0] = new Color(bufferedImage.getRGB(i - 1, j - 1));
                surroundedPixel[1] = new Color(bufferedImage.getRGB(i - 1, j));
                surroundedPixel[2] = new Color(bufferedImage.getRGB(i - 1, j + 1));
                surroundedPixel[3] = new Color(bufferedImage.getRGB(i, j + 1));
                surroundedPixel[4] = new Color(bufferedImage.getRGB(i + 1, j + 1));
                surroundedPixel[5] = new Color(bufferedImage.getRGB(i + 1, j));
                surroundedPixel[6] = new Color(bufferedImage.getRGB(i + 1, j - 1));
                surroundedPixel[7] = new Color(bufferedImage.getRGB(i, j - 1));
                surroundedPixel[8] = new Color(bufferedImage.getRGB(i, j));
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
    }
    
    public void createImage(){
        try {
            ImageIO.write(this.image, this.getExtension(), new File(this.output));
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
