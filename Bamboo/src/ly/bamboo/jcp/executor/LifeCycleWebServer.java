package ly.bamboo.jcp.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 使用线程池接收socket请求
 */
public class LifeCycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while(!exec.isShutdown()) {
            try {
                final Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleRequest(conn);
                    }
                });
            } catch (RejectedExecutionException e) { //解决并发时shutdown后，isShutdown没来得及返回true的情况
                if(!exec.isShutdown())
                    System.out.println("task submission rejected" + e);
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    void handleRequest(Socket connection) {
        System.out.println("handle request");
    }
}
