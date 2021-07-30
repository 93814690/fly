package top.liyf.redis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
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

    /**
     * 功能描述: 删除缓存
     * 
     * @param key
     * @return java.lang.Boolean
     * @author liyf
     */
    public Boolean delete(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.delete(realKey);
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

    // =================== List ===================

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

    // =================== Set ===================

    /**
     * 功能描述: 向集合添加一个或多个成员
     *
     * @param key
     * @param value
     * @return java.lang.Long
     * @author liyf
     */
    public Long sAdd(String key, Object... value) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForSet().add(realKey, value);
    }

    /**
     * 功能描述: 返回集合中的所有成员
     *
     * @param key
     * @return java.util.Set<java.lang.Object>
     * @author liyf
     */
    public Set<Object> sGet(String key) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForSet().members(realKey);
    }

    /**
     * 功能描述: 返回集合中的所有成员
     *
     * @param key
     * @return java.util.Set<java.lang.String>
     * @author liyf
     */
    public Set<String> sGetString(String key) {
        String realKey = getRealKey(key);
        return stringTemplate.opsForSet().members(realKey);
    }

    /**
     * 功能描述: 判断 member 元素是否是集合 key 的成员
     *
     * @param key
     * @param member
     * @return java.lang.Boolean
     * @author liyf
     */
    public Boolean sIsMember(String key, Object member) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForSet().isMember(realKey, member);
    }

    /**
     * 功能描述: 移除集合中一个或多个成员
     * 
     * @param key
     * @param member
     * @return java.lang.Long
     * @author liyf
     */
    public Long sRemove(String key, Object... member) {
        String realKey = getRealKey(key);
        return redisTemplate.opsForSet().remove(realKey, member);
    }

    /**
     * 功能描述: 返回给定集合的并集
     * 
     * @param key1
     * @param key2
     * @return java.util.Set<java.lang.Object>
     * @author liyf
     */
    public Set<Object> sUnion(String key1, String key2) {
        String realKey1 = getRealKey(key1);
        String realKey2 = getRealKey(key2);
        return redisTemplate.opsForSet().union(realKey1, realKey2);
    }

    /**
     * 功能描述: 返回给定集合的并集
     *
     * @param key1
     * @param key2
     * @return java.util.Set<java.lang.String>
     * @author liyf
     */
    public Set<String> sUnionString(String key1, String key2) {
        String realKey1 = getRealKey(key1);
        String realKey2 = getRealKey(key2);
        return stringTemplate.opsForSet().union(realKey1, realKey2);
    }

    /**
     * 功能描述: 返回第一个集合与其他集合之间的差异，也可以认为说第一个集合中独有的元素
     *
     * @param key1
     * @param key2
     * @return java.util.Set<java.lang.Object>
     * @author liyf
     */
    public Set<Object> sDiff(String key1, String key2) {
        String realKey1 = getRealKey(key1);
        String realKey2 = getRealKey(key2);
        return redisTemplate.opsForSet().difference(realKey1, realKey2);
    }

    /**
     * 功能描述: 返回第一个集合与其他集合之间的差异，也可以认为说第一个集合中独有的元素
     *
     * @param key1
     * @param key2
     * @return java.util.Set<java.lang.String>
     * @author liyf
     */
    public Set<String> sDiffString(String key1, String key2) {
        String realKey1 = getRealKey(key1);
        String realKey2 = getRealKey(key2);
        return stringTemplate.opsForSet().difference(realKey1, realKey2);
    }

    private String getRealKey(String key) {
        return applicationName + ":" + key;
    }
}
