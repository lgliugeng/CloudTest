package com.liugeng.cloud.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService implements IRedisService {


    @Autowired
   private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean set(final String key, final Object value) {
       boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                redisConnection.set(redisSerializer.serialize(key),redisSerializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public <T> Set<T> keys(final String pattern) {
        Set<T> result = redisTemplate.execute(new RedisCallback<Set<T>>() {
            @Override
            public Set<T> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                Set<byte[]> value = redisConnection.keys(redisSerializer.serialize(pattern));
                Set<T> set = new HashSet<T>();
                for (byte[] v:value) {
                    set.add((T) redisSerializer.deserialize(v));
                }
                return set;
            }
        });
        return result;
    }

    @Override
    public boolean setnx(final String key, final Object value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                redisConnection.setNX(redisSerializer.serialize(key),redisSerializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean put(final String key, final Object value, final long expire) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                redisConnection.setEx(redisSerializer.serialize(key),expire,redisSerializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public <T> T get(final String key) {
        T result = redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                byte[] value = redisConnection.get(redisSerializer.serialize(key));
                return (T)redisSerializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, final long expire) {
        final long rawTimeout = TimeoutUtils.toMillis(expire, TimeUnit.SECONDS);
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer redisSerializer = redisTemplate.getDefaultSerializer();
                try {
                    return redisConnection.pExpire(redisSerializer.serialize(key),rawTimeout);
                }catch (Exception e){
                    return redisConnection.expire(redisSerializer.serialize(key),TimeoutUtils.toSeconds(expire,TimeUnit.SECONDS));
                }
            }
        },true);
    }

    @Override
    public long lpush(String key, Object obj) {
        return 0;
    }

    @Override
    public long rpush(String key, Object obj) {
        return 0;
    }

    @Override
    public <T> T lpop(String key) {
        return null;
    }

    @Override
    public <T> T rpop(String key) {
        return null;
    }

    @Override
    public long llen(String key) {
        return 0;
    }

    @Override
    public void del(String key) {

    }

    @Override
    public boolean exists(String key) {
        return false;
    }

    @Override
    public boolean isLock(String lockKey) {
        return false;
    }

    @Override
    public boolean isLockForTenSeconds(String lockKey) {
        return false;
    }

    @Override
    public void delAll() {

    }

    @Override
    public Long incrBy(String key, long longValue) {
        return null;
    }

    @Override
    public Long incrByOne(String key) {
        return null;
    }

    public Jedis getJedis(){
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.auth("Juboon123");
        return jedis;
    }
}
