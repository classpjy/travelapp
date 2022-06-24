package com.pjy.cache;

import com.pjy.utils.ApplicationContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用md5 加密减少key太长问题
 */
public final class MybatisPlusRedisCache implements Cache {
    //当前id为放入缓存的Mapper的namespace
    // 读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private final String id;

    public MybatisPlusRedisCache(String id) {
        this.id = id;
    }


    private RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        return redisTemplate;
    }

    //超时策略
    private void timeoutPolicy(String id) {
        switch (id) {
            case "com.pjy.mapper.AttractionsMapper":
                getRedisTemplate().expire(id, Duration.ofMinutes(10));
                break;
            case "com.pjy.mapper.ProvinceMapper":
                getRedisTemplate().expire(id, Duration.ofMinutes(20));
                break;
            case "com.pjy.mapper.UserMapper":
                getRedisTemplate().expire(id, Duration.ofMinutes(30));
                break;
            default:
                getRedisTemplate().expire(id, Duration.ofMinutes(-1));
        }
    }

    @Override
    //必须返回id 表示cache的唯一表示
    public String getId() {
        return this.id;
    }

    @Override
    //key(的类型CacheKey)表示当前查询sql语句的唯一id
    //value表示当前sql查询语句的结果
    public void putObject(Object key, Object value) {
        String md5Key = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        getRedisTemplate().opsForHash().put(id, md5Key, value);
        // 设置超时
        timeoutPolicy(id);
    }

    @Override
    public Object getObject(Object key) {
        String md5Key = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        return getRedisTemplate().opsForHash().get(id, md5Key);
    }

    //这个方法当发生增加修改删除时不会执行,在后续版本mybatis有可以会使用到
    @Override
    public Object removeObject(Object key) {
        String md5Key = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        Object result = getRedisTemplate().opsForHash().get(id, md5Key);
        getRedisTemplate().opsForHash().delete(id, md5Key);
        return result;
    }

    @Override
    //这个方法当发生增加修改删除时都会执行
    public void clear() {
        getRedisTemplate().delete(id);
    }

    @Override
    public int getSize() {
        return Math.toIntExact(getRedisTemplate().opsForHash().size(id));
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
