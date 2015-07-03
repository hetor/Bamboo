package ly.bamboo.jcp.producer_consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hetao on 15-6-15.
 */
public class Producer implements Runnable {
    private BlockingQueue queue;
    private volatile boolean isRunning = true;
    private AtomicInteger count = new AtomicInteger();
    private final int SLEEP_TIME = 5;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while(isRunning) {
                int i = count.incrementAndGet();
                System.out.println("try to Produce Message :" + i);
                TimeUnit.SECONDS.sleep(random.nextInt(SLEEP_TIME));

//                //offer 不阻塞立刻返回或阻塞指定时间返回
//                if(queue.offer(new Message(i), 2L, TimeUnit.SECONDS)) {
//                    System.out.println("Producing Message :" + i);
//                } else {
//                    System.err.println("fail Producing Message:" + i);
//                }

                //put 阻塞等待
                queue.put(new Message(i));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
    }
}
