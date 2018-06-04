package medianfilterparallel;

import java.util.*;
import java.util.concurrent.*;
 
/**
 * Consumer using BlockingQueue example
 * @author www.codejava.net
 *
 */
public class Consumer implements Runnable {
    private BlockingQueue<Object> queue;
    private String threadId;
 
    public Consumer(BlockingQueue<Object> queue) {
        this.queue = queue;
    }
 
    public void run() {
        threadId = "Consumer-" + Thread.currentThread().getId();
        try {
 
            while (true) {
                Object number = queue.poll(5, TimeUnit.SECONDS);
 
                if (number == null) {
                    break;
                }
 
                consume(number);
 
                Thread.sleep(1000);
            }
 
            System.out.println(threadId + " STOPPED.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
 
    private void consume(Object number) {
 
        System.out.println(threadId + ": Consuming number <= " + number);
 
    }
}