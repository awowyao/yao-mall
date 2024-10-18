package org.cwy.cloud.util;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/8/20 13:59
 */
@Component
public class RedisUtil {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public <R> R ToBean(String key, Class<R> BeanClass){
        String o = redisTemplate.opsForValue().get(key).toString();
        return JSONUtil.toBean(o, BeanClass);
    }
    public String get(String key){
        return redisTemplate.opsForValue().get(key).toString();
    }
    public long decrement(String key, Integer delta){
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    public void set(String key, Object s){
        redisTemplate.opsForValue().set(key, s);
    }
    public void set(String key, String s, Integer time, TimeUnit unit){
        redisTemplate.opsForValue().set(key, s, time, unit);
    }
    public void setBean(String key, Object bean){
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(bean));
    }
    public void setBean(String key, Object bean, Integer time, TimeUnit unit){
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(bean), time, unit);
    }

    public void execute(SessionCallback<List<Object>> sessionCallback) {
        redisTemplate.execute(sessionCallback);
    }
    public List<Object> exec() {
        return redisTemplate.exec();
    }
    public void setHase(String key, String hashKey, Object data) {
        redisTemplate.opsForHash().put(key, hashKey, data);
    }
    public void setHase(String key, String hashKey, Object data, Integer time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, hashKey, data);
        redisTemplate.expire(key, time, timeUnit);
    }
    public boolean hasKeyHase(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public void deleteKeyHase(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }
    public void setHaseAb(String key, String hashKey, Object data) {
        redisTemplate.opsForHash().put(key, hashKey, data);
    }
    public String getHase(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey).toString();
    }

    public Map<Object, Object> getHaseAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void setZSet(String key, Object data, double score) {
        redisTemplate.opsForZSet().add(key, data, score);
    }

    public void setZSet(String key, Object data, double score, Integer time, TimeUnit timeUnit) {
        redisTemplate.opsForZSet().add(key, data, score);
        redisTemplate.expire(key, time, timeUnit);
    }

    public Set<Object> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    public Set<Object> getZSetIndex(String key, Integer start, Integer end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Long removeZSet(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }
}
