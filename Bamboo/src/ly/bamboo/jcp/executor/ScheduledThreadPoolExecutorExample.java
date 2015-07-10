package ly.bamboo.jcp.executor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 替代Timer,DelayQueue中的任务只有当逾期后才能被take出来执行。
 */
public class ScheduledThreadPoolExecutorExample {
    private static final ScheduledThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(10);


    public void rend() {
        //执行的过程中抛出任何的异常，后续的执行就不再继续了，所以要处理好异常
        EXECUTOR.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("do do do!");
                try {
                    doBusiness();
                } catch (Throwable th) {
                    //handle exception
                }
            }
        }, 2, 1, TimeUnit.SECONDS);

        EXECUTOR.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("ao, ao, ao");
                try {
                    doBusiness();
                } catch (Throwable th) {
                    //handle exception
                }
            }
        }, 2, 2,TimeUnit.SECONDS);
    }

    private void doBusiness() {
        //may throw exception
    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutorExample example = new ScheduledThreadPoolExecutorExample();
        example.rend();
    }

}
