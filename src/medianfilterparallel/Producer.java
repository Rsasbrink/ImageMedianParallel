package medianfilterparallel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Producer using BlockingQueue example
 *
 * @author www.codejava.net
 *
 */
public class Producer implements Runnable {

    private BlockingQueue<Object> queue;
    private Image image;
    private int packageAmount;

    public Producer(BlockingQueue<Object> queue, Image image, int packageAmount) {
        this.queue = queue;
        this.image = image;
        this.packageAmount = packageAmount;
    }

    public void run() {
        try {
            for (int i = 0; i < 1; i++) {
                produce();
                          

            }
            System.out.println("hi");
          
           

            System.out.println("Producer STOPPED.");

        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produce() throws IOException, InterruptedException {
        File imageFile = new File(image.getInput());
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        int modulo = 0;
        if (image.getImage() == null) {
            image.setImage(new BufferedImage(imageWidth, imageHeight, bufferedImage.getType()));
        }
        System.out.println("1");
        final int heightPerPackage = imageHeight / packageAmount;
        modulo = imageHeight % packageAmount;
        System.out.println("2");
        for (int i = 0; i <= packageAmount; i++) {
            System.out.println("3" + i);
            final int ii = i;
            Object currentObject;
            currentObject = new Object() {
                int offsetHeight = heightPerPackage * ii;
                int height = heightPerPackage;
                int width = imageWidth;
            };
            queue.put(currentObject);
        }
        System.out.println("4");
        if (modulo > 0) {
            Object moduloObject = new Object() {
                int height = imageHeight - (packageAmount * heightPerPackage);
                int width = imageWidth;
            };
            queue.put(moduloObject);
        }
        System.out.println("5");
         Object emptyObject = new Object() {
                int height = 0;
                int width = 0;
            };
         System.out.println("6");
         queue.put(emptyObject);
         System.out.println("7");
    }
}
