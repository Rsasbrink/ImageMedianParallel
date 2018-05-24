/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medianfilterparallel;

/**
 *
 * @author rowanvi
 *
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

class MedianFilterParallel {

    //PICTURES http://people.sc.fsu.edu/~jburkardt/data/pgma/pgma.html
    public static void main(String[] a) throws Throwable {
        final String fileType = "png";
        final String file = "input2.png";
        final String outputFile = "output2.png";
        final int threadLength = 4;
        final Thread[] threads = new Thread[threadLength];
        long startTime = System.currentTimeMillis();
        final Image image = new Image(file, outputFile, fileType);
        BufferedImage outputImage = null;
        BufferedImage splitTemp[] = new BufferedImage[threadLength];
       
        try {
            splitTemp = image.splitImage();
        } catch (Exception ex) {
            Logger.getLogger(MedianFilterParallel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        final BufferedImage imgs[] = splitTemp;
        


        class FilterThread extends Thread {
            

            @Override
            public void run() {
                
                try {
                    BufferedImage tempImgs[] = new BufferedImage[imgs.length];
                    int count = MedianFilterParallel.getThreadCount();
                    tempImgs[count] = imgs[count];

                    image.applyMedianFilterOnOtherImages(tempImgs[count]);
                } catch (Exception ex) {
                    Logger.getLogger(MedianFilterParallel.class.getName()).log(Level.SEVERE, null, ex);
                }
                    

            }
        }
        
        for (int i = 0; i < threads.length; i++) {
            MedianFilterParallel.threadCount = i;
            threads[i] = new FilterThread();
            threads[i].start();
            
        }
         for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        try {
            image.combineChunks(imgs);
        } catch (IOException ex) {
            Logger.getLogger(MedianFilterParallel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ImageIO.write(image.getImg(), image.getFileType(), image.getOutputCreatedFile());
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);

        System.out.println("Duration " + duration);
    }
    
    private static int threadCount = 0;

    public static synchronized int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

}
