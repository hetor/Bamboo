package ly.bamboo.jcp.concurrent_tools;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-6-30.
 */
public class CountDownLatchExample {
    private final int THREAD_NUM = 10;
    private final CountDownLatch startGate = new CountDownLatch(1);
    private final CountDownLatch endGate = new CountDownLatch(THREAD_NUM);

    private void beginRace(final Runnable task) throws Exception {
        for(int i=0; i<THREAD_NUM; i++) {
            Thread runner = new Thread("player_" + (i + 1)) {
                @Override
                public void run() {
                    try {
                        startGate.await();

                        long start = System.currentTimeMillis();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                        long end = System.currentTimeMillis();

                        System.out.println(Thread.currentThread().getName() + " record: " + (end - start));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            runner.start();
        }

        System.out.println("running race begin");
        startGate.countDown();
        endGate.await();
        System.out.println("running race end");
    }

    public static void main(String[] args) throws Exception {
        CountDownLatchExample runningRace = new CountDownLatchExample();
        final Random random = new Random();

        runningRace.beginRace(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000 + random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
