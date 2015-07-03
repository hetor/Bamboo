package ly.bamboo.jcp.concurrent_tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-7-1.
 */
public class CyclicBarrierExample {
    private CyclicBarrier cyclicBarrier;
    private List<ComputeTask> tasks;

    public CyclicBarrierExample(List<ComputeTask> tasks) {
        this.tasks = tasks;
        init();
    }

    private void init() {
        cyclicBarrier = new CyclicBarrier(tasks.size(), new Runnable() {
            @Override
            public void run() {
                System.out.println("all sub tasks is done!");
                long result = 0l;
                for (ComputeTask task : tasks) {
                    result += task.getResult();
                }
                System.out.println("result is: " + result);
            }
        });
    }

    public void doTask() {
        for (final ComputeTask task : tasks) {
            Thread thread = new Thread(task) {
                @Override
                public void run() {
                    super.run();

                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
    }

    public static void main(String[] args) {
        ComputeTask task0 = new ComputeTask() {
            @Override
            public void run() {
                long result = 0l;
                for(int i=0; i<100; i++) {
                    result += i;
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("task 0 result is:" + result);
                this.setResult(result);
            }
        };

        ComputeTask task1 = new ComputeTask() {
            @Override
            public void run() {
                long result = 0l;
                for(int i=100; i<400; i++) {
                    result += i;
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("task 1 result is:" + result);
                this.setResult(result);
            }
        };

        ComputeTask task2 = new ComputeTask() {
            @Override
            public void run() {
                long result = 0l;
                for(int i=400; i<1000; i++) {
                    result += i;
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("task 2 result is:" + result);
                this.setResult(result);
            }
        };

        List<ComputeTask> tasks = new ArrayList<>();
        tasks.add(task0);
        tasks.add(task1);
        tasks.add(task2);

        CyclicBarrierExample example = new CyclicBarrierExample(tasks);
        example.doTask();
    }

    private abstract static class ComputeTask implements Runnable {
        private long result;

        public long getResult() {
            return result;
        }

        public void setResult(long result) {
            this.result = result;
        }
    }
}
