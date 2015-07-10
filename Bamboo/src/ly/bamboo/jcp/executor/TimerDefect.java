package ly.bamboo.jcp.executor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 1. 单线程
 * 2. 任务的异常会导致整个timer崩溃
 */
public class TimerDefect {

    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();

        timer.schedule(new ThrowTask("task-1", 5), 1);

        timer.schedule(new ThrowTask("task-2", 1), 1);
    }

    private static class ThrowTask extends TimerTask {

        private final String name;
        private final long sleepTime;

        public ThrowTask(final String name, final long sleepTime) {
            this.name = name;
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
                System.out.println(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }
    }
}
