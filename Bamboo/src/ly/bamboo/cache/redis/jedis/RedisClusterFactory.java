package ly.bamboo.cache.redis.jedis;

import redis.clients.jedis.*;

/**
 * Created by hetao on 15-7-13.
 */
import java.util.HashSet;
import java.util.Set;

public class RedisClusterFactory {

    private static final class JedisClusterHolder {
        private static JedisCluster cluster;

        static {
            init();
        }

        private static void init() {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(100);
            config.setMaxIdle(100);
            config.setMinIdle(100);
            config.setMaxWaitMillis(6 * 1000);
            config.setTestOnBorrow(true);

            Set<HostAndPort> clusterNodes = new HashSet<>();
            clusterNodes.add(new HostAndPort("127.0.0.1", 7000));
            clusterNodes.add(new HostAndPort("127.0.0.1", 7001));
            clusterNodes.add(new HostAndPort("127.0.0.1", 7002));
            clusterNodes.add(new HostAndPort("127.0.0.1", 7003));
            clusterNodes.add(new HostAndPort("127.0.0.1", 7004));
            clusterNodes.add(new HostAndPort("127.0.0.1", 7005));

            cluster = new JedisCluster(clusterNodes, 2000, 2, config);
        }
    }

    public final JedisCluster getCluster() {
        return JedisClusterHolder.cluster;
    }
}