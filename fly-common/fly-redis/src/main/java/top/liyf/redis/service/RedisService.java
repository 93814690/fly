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


    private String getRealKey(String key) {
        return applicationName + ":" + key;
    }
}
