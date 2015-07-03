package ly.bamboo.jcp.sync_problem;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by hetao on 15-6-29.
 * 同步容器迭代时需要对容器加锁
 */
public class SyncContainerProblem {

    private static class VectorRemove implements Runnable {

        private Vector vector;

        public VectorRemove(Vector vector) {
            this.vector = vector;
        }

        @Override
        public void run() {
            vector.remove(vector.size());
        }
    }

    private static class VectorIterator implements Runnable {

        private Vector vector;

        public VectorIterator(Vector vector) {
            this.vector = vector;
        }

        @Override
        public void run() {
            Iterator iterator = vector.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }


    public static void main(String[] args) {
        Vector vector = new Vector();
        for (int i=0; i<1000; i++) {
            vector.add(i);
        }

        new Thread(new VectorIterator(vector), "t_VectorIterator").start();
        new Thread(new VectorRemove(vector), "t_VectorRemove").start();
    }
}
