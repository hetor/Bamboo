package ly.bamboo.cache.memcache.ganda;

/**
 * Created by hetao on 15-7-17.
 */
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class MemcachedClusters {
    private static MemCachedClient memCachedClient = new MemCachedClient();

    // 设置与缓存服务器的连接池
    static {
        // 服务器列表和其权重
        String[] servers = {"10.3.40.75:11211", "10.3.40.75:11212", "10.3.40.75:11213"};
//        Integer[] weights = { 1 };

        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers(servers);
//        pool.setWeights(weights);

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(250);
        pool.setMaxIdle(1000 * 60 * 60 * 6);

        // 设置主线程的睡眠时间
        pool.setMaintSleep(30);

        // 这是开启一个nagle算法。该算法避免网络中充塞小封包，提高网络的利用率
        pool.setNagle(true);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);
        pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);
        pool.setFailover(true);
        pool.setAliveCheck(true);

        // 初始化连接池
        pool.initialize();
    }

    public static void main(String[] args) {
        int totalCount = 100000;

        //test add
//        for (int i = 0; i < totalCount; i++) {
//            memCachedClient.add("ht2" + i, "good" + i);
//        }

        //test get
        int nullCount = 0;
        for (int i = 0; i < totalCount; i++) {
            Object o = memCachedClient.get("ht2" + i);
            if(null == o) {
                nullCount++;
            }
            System.out.println(o);
        }
        System.out.println("fail count: " + nullCount);

        //stats
        System.out.println(memCachedClient.stats());
    }

}
