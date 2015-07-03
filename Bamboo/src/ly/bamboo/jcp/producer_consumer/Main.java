package ly.bamboo.jcp.producer_consumer;

import java.util.concurrent.*;

/**
 * Created by hetao on 15-6-15.
 */
public class Main {

    /**
     * try to Produce Message :1
     * consuming message:1
     * try to Produce Message :2
     * consuming message:2
     * try to Produce Message :3
     * consuming message:3
     * try to Produce Message :4
     * consuming message:4
     */
    private static void useSynchronousQueue() throws Exception {
        BlockingQueue<Message> queue = new SynchronousQueue<>();
        Producer producer0 = new Producer(queue);
        Consumer consumer0 = new Consumer(queue);

        new Thread(producer0).start();
        TimeUnit.SECONDS.sleep(3);
        new Thread(consumer0).start();

        TimeUnit.SECONDS.sleep(10);
        producer0.stop();
        consumer0.stop();
    }

    private static void useArrayBlockingQueue() throws Exception {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);

        Producer producer0 = new Producer(queue);
        Consumer consumer0 = new Consumer(queue);

        new Thread(producer0).start();
        new Thread(producer0).start();
        new Thread(producer0).start();

        new Thread(consumer0).start();
        new Thread(consumer0).start();

        TimeUnit.SECONDS.sleep(10);
        producer0.stop();
        consumer0.stop();
    }

    private static void useLinkedBlockingQueue() throws Exception {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

        Producer producer0 = new Producer(queue);
        Consumer consumer0 = new Consumer(queue);

        new Thread(producer0).start();
        new Thread(producer0).start();
        new Thread(producer0).start();

        new Thread(consumer0).start();
        new Thread(consumer0).start();

        TimeUnit.SECONDS.sleep(10);
        producer0.stop();
        consumer0.stop();
    }

    public static void main(String[] args) throws Exception {
        useSynchronousQueue();
    }
}
