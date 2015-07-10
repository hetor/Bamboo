package ly.bamboo.jcp.executor;

import ly.bamboo.jcp.base.TaskResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 限时invokeAll：执行一组任务，返回一组Future，invokeAll按照任务集合中的迭代器的顺序将所有的Future填到返回的集合中，
 * 当所有任务都完成时，或者调用线程被中断，又或者超出指定时限时，invokeAll将返回，当超出指定时限时，任何还未完成的任务将取消，
 * invokeAll返回后，每个任务要么执行完成，要么被取消，客户端代码可以通过调用get()或isCancelled来判断究竟是何种情况。
 */
public class InvokeAllExample {
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    public List<String> render(int num) throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<>(num);
        for(int i=0; i<num; i++) {
            tasks.add(new QuoteTask("quoteTask-" + i));
        }

        List<Future<TaskResult>> futures = EXECUTOR.invokeAll(tasks, 3, TimeUnit.SECONDS);

        List<String> results = new ArrayList<>(num);

        Iterator<QuoteTask> iterator = tasks.iterator();
        for (Future<TaskResult> future : futures) {
            QuoteTask task = iterator.next();
            try {
                results.add(future.get().toString());
            } catch (ExecutionException e) {
                results.add(e.getCause().toString());
            } catch (CancellationException e) {
                results.add(task.getName() + " is cancelled");
            }
        }

        return results;
    }

    private class QuoteTask implements Callable<TaskResult> {

        private String name;

        public QuoteTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public TaskResult call() throws Exception {
            Random random = new Random();
            int time = random.nextInt(5);
            TimeUnit.SECONDS.sleep(time);
            return new TaskResult(true, "task result: " + time);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InvokeAllExample example = new InvokeAllExample();
        System.out.println(example.render(10));

        EXECUTOR.shutdown();
    }
}
