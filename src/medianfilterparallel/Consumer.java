package medianfilterparallel;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Consumer using BlockingQueue example
 *
 * @author www.codejava.net
 *
 */
public class Consumer implements Runnable {

    private BlockingQueue<Object> queue;
    private String threadId;
    private Image image;

    public Consumer(BlockingQueue<Object> queue, Image image) {
        this.queue = queue;
        this.image = image;
    }

    public void run() {
        threadId = "Consumer-" + Thread.currentThread().getId();
        try {
            while (true) {
                Object imagePackage = queue.poll(5, TimeUnit.SECONDS);

                if (imagePackage.getClass().getDeclaredField("height").getInt(imagePackage) == 0) {
                    break;
                }

                consume(imagePackage);

                Thread.sleep(1);
            }

            System.out.println(threadId + " STOPPED.");
        } catch (InterruptedException ie) {
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consume(Object imagePackage) throws NoSuchFieldException, IllegalArgumentException, IllegalArgumentException, IllegalAccessException, Exception {
        try {
            int offsetHeight = imagePackage.getClass().getDeclaredField("offsetHeight").getInt(imagePackage);
            int height = imagePackage.getClass().getDeclaredField("height").getInt(imagePackage);
            int width = imagePackage.getClass().getDeclaredField("width").getInt(imagePackage);
            image.applyMedian(offsetHeight, height, width);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
