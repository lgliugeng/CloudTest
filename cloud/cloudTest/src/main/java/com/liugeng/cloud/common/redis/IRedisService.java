package com.liugeng.cloud.common.redis;

import java.util.Set;

public interface IRedisService {

    /**
    * 方法说明   存储value到redis
    * @方法名    set
    * @参数      [key, value]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/4/28 11:53
    * @创建人    liugeng
    */
    public boolean set(String key,Object value);

    /**
    * 方法说明   返回匹配的keys
     * KEYS * 匹配数据库中所有 key 。
     * KEYS h?llo 匹配 hello ， hallo 和 hxllo 等。
     * KEYS h*llo 匹配 hllo 和 heeeeello 等。
     * KEYS h[ae]llo 匹配 hello 和 hallo ，但不匹配 hillo 。
    * @方法名    keys
    * @参数      [pattern]
    * @返回值    java.util.Set<T>
    * @异常      
    * @创建时间  2019/4/28 11:55
    * @创建人    liugeng
    */
    public <T>Set<T> keys(String pattern);

    /**
    * 方法说明   将数据以键值对形式存储
    * @方法名    setnx
    * @参数      [key, value]
    * @返回值    boolean
    * @异常
    * @创建时间  2019/4/28 12:02
    * @创建人    liugeng
    */
    public boolean setnx(String key, Object value);

    /**
    * 方法说明   将value放入redis并设置过期时间
    * @方法名    put
    * @参数      [key, value, expire]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/4/28 12:03
    * @创建人    liugeng
    */
    public boolean put(String key, Object value, long expire);

    /**
    * 方法说明   通过key获取value值
    * @方法名    get
    * @参数      [key]
    * @返回值    T
    * @异常
    * @创建时间  2019/4/28 12:04
    * @创建人    liugeng
    */
    public <T> T get(String key);

    /**
    * 方法说明   设置key的过期时间
    * @方法名    expire
    * @参数      [key, expire]
    * @返回值    boolean
    * @异常
    * @创建时间  2019/4/28 12:07
    * @创建人    liugeng
    */
    public boolean expire(String key, long expire);

    /**
    * 方法说明   向队列key中插入value (左边)
    * @方法名    lpush
    * @参数      [key, obj]
    * @返回值    long
    * @异常
    * @创建时间  2019/4/28 12:18
    * @创建人    liugeng
    */
    public long lpush(String key, Object obj);

    /**
    * 方法说明   向队列key中插入value (右边)
    * @方法名    rpush
    * @参数      [key, obj]
    * @返回值    long
    * @异常      
    * @创建时间  2019/4/28 12:18
    * @创建人    liugeng
    */
    public long rpush(String key, Object obj);

    /**
    * 方法说明   移除并返回key中的元素（左边）
    * @方法名    lpop
    * @参数      [key]
    * @返回值    T
    * @异常
    * @创建时间  2019/4/28 12:21
    * @创建人    liugeng
    */
    public <T> T lpop(String key);

    /**
    * 方法说明   移除并返回key中的元素（右边）
    * @方法名    rpop
    * @参数      [key]
    * @返回值    T
    * @异常
    * @创建时间  2019/4/28 12:22
    * @创建人    liugeng
    */
    public <T> T rpop(String key);

    /**
    * 方法说明   返回key中列表长度
    * @方法名    llen
    * @参数      [key]
    * @返回值    long
    * @异常      
    * @创建时间  2019/4/28 12:22
    * @创建人    liugeng
    */
    public long llen(String key);

    /**
    * 方法说明   删除key
    * @方法名    del
    * @参数      [key]
    * @返回值    void
    * @异常
    * @创建时间  2019/4/28 12:24
    * @创建人    liugeng
    */
    public void del(String key);

    /**
    * 方法说明   判断key是否存在
    * @方法名    exists
    * @参数      [key]
    * @返回值    boolean
    * @异常
    * @创建时间  2019/4/28 12:25
    * @创建人    liugeng
    */
    public boolean exists(String key);

    /**
    * 方法说明   分布锁是否锁定(初次上锁时当前默认锁定10分钟)
    * @方法名    isLock
    * @参数      [lockKey]
    * @返回值    boolean
    * @异常
    * @创建时间  2019/4/28 12:29
    * @创建人    liugeng
    */
    public boolean isLock(String lockKey);

    /**
    * 方法说明   分布锁是否锁定(默认锁定10秒的方法)
    * @方法名    isLockForTenSeconds
    * @参数      [lockKey]
    * @返回值    boolean
    * @异常      
    * @创建时间  2019/4/28 12:29
    * @创建人    liugeng
    */
    public boolean isLockForTenSeconds(String lockKey);

    /**
    * 方法说明   删除所有key
    * @方法名    delAll
    * @参数      []
    * @返回值    void
    * @异常
    * @创建时间  2019/4/28 12:30
    * @创建人    liugeng
    */
    public void delAll();

    /**
    * 方法说明   将key的数字值加上增量
    * @方法名    incrBy
    * @参数      [key, longValue]
    * @返回值    java.lang.Long
    * @异常
    * @创建时间  2019/4/28 14:08
    * @创建人    liugeng
    */
    public Long incrBy(String key, long longValue);

    /**
    * 方法说明   将key的数字值加上1
    * @方法名    incrByOne
    * @参数      [key]
    * @返回值    java.lang.Long
    * @异常
    * @创建时间  2019/4/28 14:09
    * @创建人    liugeng
    */
    public Long incrByOne(String key);

}
