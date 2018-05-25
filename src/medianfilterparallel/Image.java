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
import javax.imageio.ImageIO;

/**
 *
 * @author rowanvi
 */
public class Image {

    private String input;
    private String output;
    private String extension;

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
        File imageFile =  new File(this.input);
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int imageHeight = bufferedImage.getWidth();
        int imageWidth = bufferedImage.getHeight();
    
       BufferedImage finalImage = new BufferedImage(imageWidth, imageHeight, bufferedImage.getType());

//        long startTime = System.currentTimeMillis();
//        int rows = 2;   //we assume the no. of rows and cols are known and each chunk has equal width and height
//        int cols = 2;
//        int chunks = rows * cols;
//        int chunkWidth, chunkHeight;
//        chunkWidth = imgs[0].getWidth();
//        chunkHeight = imgs[0].getHeight();
//        BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, this.img.getType());
//        int num = 0;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                finalImg.createGraphics().drawImage(imgs[num], chunkWidth * j, chunkHeight * i, null);
//                num++;
//            }
//        }
//        this.setImg(finalImg);
//        applyMedianFilterOnOtherImages(this.img);
//        System.out.println("Image concatenated.....");
//        ImageIO.write(this.img, "png", new File(this.outputFile));
//        long endTime = System.currentTimeMillis();
//        long duration = (endTime - startTime);
//
//        System.out.println("Duration Image Combining Parallel " + duration);
System.out.println(bufferedImage);
    }

//    public void applyMedianFilterOnOtherImages(BufferedImage chunk) throws Exception {
//        long startTime = System.currentTimeMillis();
//        Color[] surroundedPixel = new Color[9];
//        int[] R = new int[9];
//        int[] B = new int[9];
//        int[] G = new int[9];
//        for (int i = 1; i < chunk.getWidth() - 1; i++) {
//            for (int j = 1; j < chunk.getHeight() - 1; j++) {
//                surroundedPixel[0] = new Color(chunk.getRGB(i - 1, j - 1));
//                surroundedPixel[1] = new Color(chunk.getRGB(i - 1, j));
//                surroundedPixel[2] = new Color(chunk.getRGB(i - 1, j + 1));
//                surroundedPixel[3] = new Color(chunk.getRGB(i, j + 1));
//                surroundedPixel[4] = new Color(chunk.getRGB(i + 1, j + 1));
//                surroundedPixel[5] = new Color(chunk.getRGB(i + 1, j));
//                surroundedPixel[6] = new Color(chunk.getRGB(i + 1, j - 1));
//                surroundedPixel[7] = new Color(chunk.getRGB(i, j - 1));
//                surroundedPixel[8] = new Color(chunk.getRGB(i, j));
//                for (int k = 0; k < 9; k++) {
//                    R[k] = surroundedPixel[k].getRed();
//                    B[k] = surroundedPixel[k].getBlue();
//                    G[k] = surroundedPixel[k].getGreen();
//                }
//                Arrays.sort(R);
//                Arrays.sort(G);
//                Arrays.sort(B);
//                chunk.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
//            }
//        }
//
//        this.setImg(img);
//        long endTime = System.currentTimeMillis();
//        long duration = (endTime - startTime);
//
//        System.out.println("Duration Image MEdian filter Parallel " + duration);
//    }
}
