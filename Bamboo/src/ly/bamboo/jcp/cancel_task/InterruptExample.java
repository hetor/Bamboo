package ly.bamboo.jcp.cancel_task;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-7-9.
 */
public class InterruptExample implements Runnable {
    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(p.toString());
            p = p.nextProbablePrime();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel() {
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) {

        InterruptExample interruptExample = new InterruptExample();
        try {
            new Thread(interruptExample).start();
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            interruptExample.cancel(); //在finally块中，保证cancel方法一定会被执行
        }

    }
}
