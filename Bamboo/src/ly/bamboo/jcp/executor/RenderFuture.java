package ly.bamboo.jcp.executor;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 如果task2的执行速度远远高于task1的速度，那么程序性能与串行差别不大，代码却变得更复杂了
 * 只有当大量相互独立且同构的任务可以并发进行处理时，才能体现出将程序的工作负载分配到多个任务中带来的真正性能提升。
 */
public class RenderFuture {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    public void renderPage() {
        Callable<Void> task1 = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Random random = new Random();
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (true) {
                    throw new RuntimeException("some thing is error!");
                }
                System.out.println("future task done!");
                return null;
            }
        };
        Future<Void> future = EXECUTOR.submit(task1);

        doTask2();

        try {
            future.get();
        } catch (InterruptedException e) {
            // 重新设置线程的中断状态
            Thread.currentThread().interrupt();
            // 由于不需要结果，因此取消任务
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
        } catch (CancellationException e) {
            e.printStackTrace();
        }

    }

    private void doTask2() {
        Random random = new Random();
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("other task done!");
    }

    public static void main(String[] args) {
        RenderFuture renderFuture = new RenderFuture();
        renderFuture.renderPage();

        EXECUTOR.shutdown();
    }
}
