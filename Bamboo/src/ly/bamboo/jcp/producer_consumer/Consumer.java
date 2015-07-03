package ly.bamboo.jcp.producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-6-15.
 */
public class Consumer implements Runnable {

    private BlockingQueue<Message> queue;
    private volatile boolean isRunning = true;
    private final int CONSUME_TIME = 5;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (isRunning) {
            try {
                Message message = queue.take();
                if(null != message) {
                    System.out.println("consuming message:" + message.getNum());
                    TimeUnit.SECONDS.sleep(random.nextInt(CONSUME_TIME));
                } else {
                    System.err.println("fail consuming message:" + message.getNum());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }
}
