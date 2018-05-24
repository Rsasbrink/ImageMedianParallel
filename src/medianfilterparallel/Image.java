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
    private String outputFile;
    private String fileType;
    private BufferedImage img;
    private File outputCreatedFile;

    public Image(String input, String output, String fileType) {
        this.input = input;
        this.outputFile = output;
        this.fileType = fileType;
    }

    public String getInput() {
        return input;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public File getOutputCreatedFile() {
        return outputCreatedFile;
    }

    public void setOutputCreatedFile(File file) {
        this.outputCreatedFile = file;
    }

    public BufferedImage[] splitImage() throws Exception {
        long startTime = System.currentTimeMillis();
        int rows = 2; //You should decide the values for rows and cols variables
        int cols = 2;
        int chunks = rows * cols;
        File f = new File(this.input);
        this.img = ImageIO.read(f);

        int chunkWidth = this.img.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = this.img.getHeight() / rows;

        BufferedImage imgs[] = new BufferedImage[chunks];
        int count = 0;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, this.img.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(this.img, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        System.out.println("Duration Splitimage Parallel " + duration);
        return imgs;
    }

    public void combineChunks(BufferedImage imgs[]) throws IOException, Exception {
        long startTime = System.currentTimeMillis();
        int rows = 2;   //we assume the no. of rows and cols are known and each chunk has equal width and height
        int cols = 2;
        int chunks = rows * cols;
        int chunkWidth, chunkHeight;
        chunkWidth = imgs[0].getWidth();
        chunkHeight = imgs[0].getHeight();
        BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, this.img.getType());
        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalImg.createGraphics().drawImage(imgs[num], chunkWidth * j, chunkHeight * i, null);
                num++;
            }
        }
        this.setImg(finalImg);
        applyMedianFilterOnOtherImages(this.img);
        System.out.println("Image concatenated.....");
        ImageIO.write(this.img, "png", new File(this.outputFile));
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        System.out.println("Duration Image Combining Parallel " + duration);
    }

    public void applyMedianFilterOnOtherImages(BufferedImage chunk) throws Exception {
        long startTime = System.currentTimeMillis();
        Color[] surroundedPixel = new Color[9];
        int[] R = new int[9];
        int[] B = new int[9];
        int[] G = new int[9];
        for (int i = 1; i < chunk.getWidth() - 1; i++) {
            for (int j = 1; j < chunk.getHeight() - 1; j++) {
                surroundedPixel[0] = new Color(chunk.getRGB(i - 1, j - 1));
                surroundedPixel[1] = new Color(chunk.getRGB(i - 1, j));
                surroundedPixel[2] = new Color(chunk.getRGB(i - 1, j + 1));
                surroundedPixel[3] = new Color(chunk.getRGB(i, j + 1));
                surroundedPixel[4] = new Color(chunk.getRGB(i + 1, j + 1));
                surroundedPixel[5] = new Color(chunk.getRGB(i + 1, j));
                surroundedPixel[6] = new Color(chunk.getRGB(i + 1, j - 1));
                surroundedPixel[7] = new Color(chunk.getRGB(i, j - 1));
                surroundedPixel[8] = new Color(chunk.getRGB(i, j));
                for (int k = 0; k < 9; k++) {
                    R[k] = surroundedPixel[k].getRed();
                    B[k] = surroundedPixel[k].getBlue();
                    G[k] = surroundedPixel[k].getGreen();
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                chunk.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
            }
        }

        this.setImg(img);
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        System.out.println("Duration Image MEdian filter Parallel " + duration);
    }
}
