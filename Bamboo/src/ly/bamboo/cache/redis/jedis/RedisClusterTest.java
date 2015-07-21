package ly.bamboo.cache.redis.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hetao on 15-7-14.
 */
public class RedisClusterTest {

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(100);
        config.setMinIdle(100);
        config.setMaxWaitMillis(6 * 1000);
        config.setTestOnBorrow(true);

        Set<HostAndPort> clusterNodes = new HashSet<>();
        clusterNodes.add(new HostAndPort("192.168.164.131", 6381));
        clusterNodes.add(new HostAndPort("10.3.40.75", 6378));
//        clusterNodes.add(new HostAndPort("127.0.0.1", 7002));
//        clusterNodes.add(new HostAndPort("127.0.0.1", 7003));
//        clusterNodes.add(new HostAndPort("127.0.0.1", 7004));
//        clusterNodes.add(new HostAndPort("127.0.0.1", 7005));

        JedisCluster redisCluster = new JedisCluster(clusterNodes, 2000, 2, config);
        try {
            for (int i = 0; i < 10000; i++) {
                long t1 = System.currentTimeMillis();
                redisCluster.set("hetao_" + i, String.valueOf(i));
                long t2 = System.currentTimeMillis();
                String value = redisCluster.get("hetao_" + i);
                long t3 = System.currentTimeMillis();
                System.out.println(value);
                System.out.println((t2 - t1) + "mills");
                System.out.println((t3 - t2) + "mills");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisCluster. close();
        }
    }
}
