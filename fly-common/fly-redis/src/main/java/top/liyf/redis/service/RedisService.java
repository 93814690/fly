package top.liyf.redis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author liyf
 * Created in 2021-05-28
 */
@Service
public class RedisService {

    private final StringRedisTemplate stringTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name}")
    private String applicationName;


    public RedisService(StringRedisTemplate stringTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.stringTemplate = stringTemplate;
        this.redisTemplate = redisTemplate;
    }


    public void setString(String key, String value, long time, TimeUnit timeUnit) {
        String realKey = getRealKey(key);
        stringTemplate.opsForValue().set(realKey, value, time, timeUnit);
    }

    public void setString(String key, String value) {
        String realKey = getRealKey(key);
        stringTemplate.opsForValue().set(realKey, value);
    }

    public String getString(String key) {
        String realKey = getRealKey(key);
        return stringTemplate.opsForValue().get(realKey);
    }

    public void set(String key, Object object){
        String realKey = getRealKey(key);
        redisTemplate.opsForValue().set(realKey,object);
    }

    public Object get(String key){
        String realKey = getRealKey(key);
        return redisTemplate.opsForValue().get(realKey);
    }

    /**
     * 功能描述: 将一个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @author liyf
     */
    public void lPushString(String key, String value) {
        String realKey = getRealKey(key);
        stringTemplate.opsForList().leftPush(realKey, value);
    }

    /**
     * 功能描述: 移除并返回列表 key 的尾元素
     *
     * @param key
     * @return java.lang.String
     * @author liyf
     */
    public String rPopString(String key) {
        String realKey = getRealKey(key);
        return stringTemplate.opsForList().rightPop(realKey);
    }

    /**
     * 功能描述: 将一个值 value 插入到列表 key 的表头
     *
     * @param key
     * @param value
     * @author liyf
     */
    public void lPush(String key, Object value) {
        String realKey = getRealKey(key);
        redisTemplate.opsForList().leftPush(realKey, value);
    }

    /**
     * 功能描述: 移除并返回列表 key 的尾元素
     * 
     * @param key
     * @return java.lang.Object
     * @author liyf
     */
    public Object rPop(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForList().rightPop(realKey);
    }

    /**
     * 功能描述: 将一个值 value 插入到列表 key 的表尾(最右边)
     * 
     * @param key
     * @param value
     * @author liyf
     */
    public void rPushString(String key, String value) {
        String realKey = getRealKey(key);
        stringTemplate.opsForList().rightPush(realKey, value);
    }

    /**
     * 功能描述: 移除并返回列表 key 的头元素
     * 
     * @param key
     * @return java.lang.String
     * @author liyf
     */
    public String lPopString(String key) {
        String realKey = getRealKey(key);
        return stringTemplate.opsForList().leftPop(realKey);
    }

    /**
     * 功能描述: 将一个值 value 插入到列表 key 的表尾(最右边)
     *
     * @param key
     * @param value
     * @author liyf
     */
    public void rPush(String key, Object value) {
        String realKey = getRealKey(key);
        redisTemplate.opsForList().rightPush(realKey, value);
    }

    /**
     * 功能描述: 移除并返回列表 key 的头元素
     *
     * @param key
     * @return java.lang.Object
     * @author liyf
     */
    public Object lPop(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForList().leftPop(realKey);
    }


    private String getRealKey(String key) {
        return applicationName + ":" + key;
    }
}
