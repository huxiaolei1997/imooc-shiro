package com.xlh.shiro.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author xiaolei hu
 * @date 2019/1/27 18:54
 **/
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private JedisUtil() {
    }

    public void set(byte[] key, byte[] value) {
        try (Jedis jedis = getResource()) {
            jedis.set(key, value);
        }
    }

    public void expire(byte[] key, int expireTime) {
        try (Jedis jedis = getResource()) {
            jedis.expire(key, expireTime);
        }
    }

    private Jedis getResource() {
        return jedisPool.getResource();
    }

    public byte[] get(byte[] key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    public void del(byte[] key) {
        try (Jedis jedis = getResource()) {
            jedis.del(key);
        }
    }

    public Set<byte[]> keys(String prefix) {
        try (Jedis jedis = getResource()) {
            return jedis.keys((prefix + "*").getBytes());
        }
    }
}
