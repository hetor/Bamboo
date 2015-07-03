package ly.bamboo.jcp.concurrent_tools;

import com.sun.javafx.tk.Toolkit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-6-30.
 */
public class FutureTaskExample {

    private FutureTask<TaskResult> future = new FutureTask<>(new Callable<TaskResult>() {
        @Override
        public TaskResult call() throws Exception {
            TimeUnit.SECONDS.sleep(3);
            return new TaskResult();
        }
    });

    private TaskResult result() throws InterruptedException {
        try {
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if(cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else if(cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if(cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new IllegalStateException("Not unchecked", cause);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        FutureTaskExample futureTaskExample = new FutureTaskExample();
        new Thread(futureTaskExample.future).start();
        futureTaskExample.result();
    }


    private class TaskResult {}

    private class DataLoadException extends RuntimeException {}
}
