/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medianfilterparallel;

import java.awt.Color;
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

    private Object lock1 = new Object();
    private Object lock2 = new Object();

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

    public void applyMedianFilterOnOtherImages() throws Exception {
        synchronized (lock1) {
            File f = new File(this.input);                               //Input Photo File
            Color[] surroundedPixel = new Color[9];
            int[] R = new int[9];
            int[] B = new int[9];
            int[] G = new int[9];
            this.outputCreatedFile = new File(this.outputFile);
            this.img = ImageIO.read(f);
            for (int i = 1; i < this.img.getWidth() - 1; i++) {
                for (int j = 1; j < this.img.getHeight() - 1; j++) {
                    surroundedPixel[0] = new Color(this.img.getRGB(i - 1, j - 1));
                    surroundedPixel[1] = new Color(this.img.getRGB(i - 1, j));
                    surroundedPixel[2] = new Color(this.img.getRGB(i - 1, j + 1));
                    surroundedPixel[3] = new Color(this.img.getRGB(i, j + 1));
                    surroundedPixel[4] = new Color(this.img.getRGB(i + 1, j + 1));
                    surroundedPixel[5] = new Color(this.img.getRGB(i + 1, j));
                    surroundedPixel[6] = new Color(this.img.getRGB(i + 1, j - 1));
                    surroundedPixel[7] = new Color(this.img.getRGB(i, j - 1));
                    surroundedPixel[8] = new Color(this.img.getRGB(i, j));
                    for (int k = 0; k < 9; k++) {
                        R[k] = surroundedPixel[k].getRed();
                        B[k] = surroundedPixel[k].getBlue();
                        G[k] = surroundedPixel[k].getGreen();
                    }
                    Arrays.sort(R);
                    Arrays.sort(G);
                    Arrays.sort(B);
                    this.img.setRGB(i, j, new Color(R[4], B[4], G[4]).getRGB());
                }
            }
        }
        synchronized (lock2) {
            
            this.setImg(img);
        }

    }

//NOT IN USE FOR NOW
    public void applyMedianFilterOnPGM() throws FileNotFoundException, IOException {
        String filePath = this.input;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        File output = new File(outputFile);

        Scanner scan = new Scanner(fileInputStream);
        // Discard the magic number
        scan.nextLine();
        // Discard the comment line
        scan.nextLine();
        // Read pic width, height and max value
        int picWidth = scan.nextInt();
        int picHeight = scan.nextInt();
        int maxvalue = scan.nextInt();

        fileInputStream.close();

        // Now parse the file as binary data
        fileInputStream = new FileInputStream(filePath);
        DataInputStream dis = new DataInputStream(fileInputStream);

        // look for 4 lines (i.e.: the header) and discard them
        int numnewlines = 4;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char) (dis.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }

        // read the image data
        int[][] data2D = new int[picHeight][picWidth];
        for (int row = 0; row < picHeight; row++) {
            for (int col = 0; col < picWidth; col++) {
                data2D[row][col] = dis.readUnsignedByte();
                System.out.print(data2D[row][col] + " ");
            }

            System.out.println();

        }
        System.out.println("HIER MOET IE NOG AANGEMAAKT WORDEN MET MEDIAN FILTER");
    }
}
