package ly.bamboo.jcp.cancel_task;


import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * 已请求取消标记
 */
public class CancelledFlag implements Runnable {
    private volatile boolean cancelled = false;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
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
        this.cancelled = true;
    }

    public static void main(String[] args) {

        CancelledFlag cancelledFlag = new CancelledFlag();
        try {
            new Thread(cancelledFlag).start();
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cancelledFlag.cancel(); //在finally块中，保证cancel方法一定会被执行
        }

    }
}
