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
        final String extension = ".png";
        final String input = "input2.png";
        final String output = input + "median-applied" + extension;
       
        final int threadsAmount = 4;

        long startTime = System.currentTimeMillis();
        final Image image = new Image(input, output, extension);

        image.applyMedian(threadsAmount);

    }
}