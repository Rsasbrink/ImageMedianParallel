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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.*;

class MedianFilterParallel {

    //PICTURES http://people.sc.fsu.edu/~jburkardt/data/pgma/pgma.html
    public static void main(String[] a) throws Throwable {
        long startTime = System.currentTimeMillis();
        final String extension = "png";
        final String input = "input2.png";
        final String output = "output2.png";
        final Image image = new Image(input, output, extension);
        final int packageAmount = 50;
        final int consumerAmount = 5;
        final Thread[] consumers = new Thread[consumerAmount];
        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(packageAmount + consumerAmount + 1);

        Thread producer = new Thread(new Producer(queue, image, packageAmount, consumerAmount));
        producer.start();
        producer.join();
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Thread(new Consumer(queue, image));
            consumers[i].start();
        }
        for (Thread consumer : consumers) {
            consumer.join();
        }
        image.createImage();

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("duration: " + duration);
    }
}
