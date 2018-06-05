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
    private int consumerAmount;

    public Producer(BlockingQueue<Object> queue, Image image, int packageAmount, int consumerAmount) {
        this.queue = queue;
        this.image = image;
        this.packageAmount = packageAmount;
        this.consumerAmount = consumerAmount;
    }

    public void run() {
        try {

            produce();

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
        image.setOriginalImage(bufferedImage);
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        int modulo;
        if (image.getImage() == null) {
            image.setImage(new BufferedImage(imageWidth, imageHeight, bufferedImage.getType()));
        }
        final int heightPerPackage = imageHeight / packageAmount;
        modulo = imageHeight % packageAmount;
        for (int i = 0; i < packageAmount; i++) {

            final int ii = i;
            Object currentObject;
            currentObject = new Object() {
                int offsetHeight = heightPerPackage * ii;
                int height = heightPerPackage;
                int width = imageWidth;
            };
            queue.put(currentObject);
        }

        if (modulo > 0) {
            Object moduloObject = new Object() {
                int offsetHeight = packageAmount * heightPerPackage;
                int height = imageHeight - (packageAmount * heightPerPackage);
                int width = imageWidth;
            };
            queue.put(moduloObject);
        }
        for (int i = 0; i < consumerAmount; i++) {
            Object emptyObject = new Object() {
                int height = 0;
                int width = 0;
            };

            queue.put(emptyObject);
        }

    }
}
