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
        final String extension = "png";
        final String input = "input1.png";
        final String output = "output1.png";

        final int threadsAmount = 200;
        final Thread[] threads = new Thread[threadsAmount];

        long startTime = System.currentTimeMillis();
        final Image image = new Image(input, output, extension);

        class filterThread extends Thread {

            public void run() {
                try {
                    image.applyMedian(threadsAmount);
                } catch (Exception ex) {
                    Logger.getLogger(MedianFilterParallel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        for (int i = 0; i < threadsAmount; i++) {
            threads[i] = new filterThread();
            threads[i].start();
        }
        for (int i = 0; i < threadsAmount; i++) {
            threads[i].join();

        }
        image.createImage();

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration: " + duration);
    }
}
