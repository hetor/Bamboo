package ly.bamboo.jcp.executor;

import ly.bamboo.jcp.base.TaskResult;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 提交一组任务，并处理结果
 */
public class CompleteServiceExample {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(10);

    public void render(final int taskNum) {
        CompletionService<TaskResult> completionService = new ExecutorCompletionService<>(EXECUTOR);

        for(int i=0; i<taskNum; i++) {
            completionService.submit(new Callable<TaskResult>() {
                @Override
                public TaskResult call() throws Exception {

                    //do business
                    Random random = new Random();
                    TimeUnit.SECONDS.sleep(random.nextInt(5));
                    System.out.println("task complete");

                    //build result
                    TaskResult taskResult = new TaskResult();
                    taskResult.setIsSuccuess(true);
                    taskResult.setMessage("task success");
                    return taskResult;
                }
            });
        }


        try {
            for (int i=0; i<taskNum; i++) {
                Future<TaskResult> future = completionService.take();
                TaskResult result = future.get();
                System.out.println(result);
            }
        } catch (InterruptedException e) {
            // 重新设置线程的中断状态
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.println(e.getCause());
        } catch (CancellationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CompleteServiceExample completeServiceExample = new CompleteServiceExample();
        completeServiceExample.render(10);
        long end = System.currentTimeMillis();

        System.out.println(end - start);

        EXECUTOR.shutdown();
    }
}
