package ly.bamboo.jcp.executor;

import ly.bamboo.jcp.base.TaskResult;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 为任务设置时限，超出就终止
 */
public class LimitTimeTaskExample {
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public void render() {
        Future<TaskResult> future = EXECUTOR.submit(new Callable<TaskResult>() {
            @Override
            public TaskResult call() throws Exception {
                Random random = new Random();
                TimeUnit.SECONDS.sleep(random.nextInt(5));

                TaskResult result = new TaskResult();
                result.setIsSuccuess(true);
                result.setMessage("task result message");
                return result;
            }
        });

        doTask2();

        try {
            TaskResult taskResult = future.get(2, TimeUnit.SECONDS);
            System.out.println(taskResult);
        } catch (InterruptedException e) {
            // 重新设置线程的中断状态
            Thread.currentThread().interrupt();
            // 由于不需要结果，因此取消任务
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
        } catch (TimeoutException e) {
            e.printStackTrace();
            future.cancel(true); //超时任务取消掉
        } catch (CancellationException e) {
            e.printStackTrace();
        }

    }

    private void doTask2() {
        Random random = new Random();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("other task done!");
    }

    public static void main(String[] args) {
        LimitTimeTaskExample example = new LimitTimeTaskExample();
        example.render();

        EXECUTOR.shutdown();
    }
}
