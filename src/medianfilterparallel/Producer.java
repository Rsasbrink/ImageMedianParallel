package medianfilterparallel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
 
/**
 * Producer using BlockingQueue example
 * @author www.codejava.net
 *
 */
public class Producer implements Runnable {
    private BlockingQueue<Object> queue;
    private Image image;
    private int packageAmount;
    
    public Producer (BlockingQueue<Object> queue, Image image, int packageAmount) {
        this.queue = queue;
        this.image = image;
        this.packageAmount = packageAmount;
    }
 
    public void run() {
        try {
            for (int i = 0; i < 10; i++) { //DIE 10 MOET NAAR CONSUMERAMOUT MAYBE +1 MODULO AFHANKELIJK
                queue.put(produce());
 
                Thread.sleep(500);
            }
 
            queue.put(-1);  // indicates end of producing
 
            System.out.println("Producer STOPPED.");
 
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private Object produce() throws IOException {
        Integer number = new Integer((int) (Math.random() * 100));
        //HIER METHODE UITVOEREN DIE COORDINATEN RETURNED IN EEN OBJECT
 
        System.out.println("Producing number => " + number);
        File imageFile = new File(image.getInput());
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        int imageHeight = bufferedImage.getHeight();
        int imageWidth = bufferedImage.getWidth();
        int modulo = 0;
        if (image.getImage() == null) {
            image.setImage(new BufferedImage(imageWidth, imageHeight, bufferedImage.getType()));
        }
        
        int heightPerPackage = imageHeight / packageAmount;
        modulo = imageHeight % packageAmount;
        
        if(modulo > 0){
            
        }else{
            
        }
        return number;
    }
}