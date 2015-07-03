package ly.bamboo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * Created by hetao on 15-6-3.
 */
public class SocketChannelClient {

    public static void main(String[] args) {
        SocketChannel client = null;
        ByteBuffer buf = ByteBuffer.allocate(1024);

        try {
            client = SocketChannel.open();
            if (client.connect(new InetSocketAddress("127.0.0.1", 8081))) {
                buf.clear();
                buf.put("Hello Java NIO".getBytes());
                buf.flip();
                while (buf.hasRemaining()) {
                    client.write(buf);
                }
            }
            TimeUnit.SECONDS.sleep(5L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != client) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
