package com.shiro.config;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.shiro.util.JsonUtil;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



@Service
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * same as get
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        return redisTemplate.execute((RedisCallback<T>) connection -> JsonUtil.read(connection.get(key.getBytes(StandardCharsets.UTF_8)), clazz));
    }

    /**
     * same as get
     *
     * @param key
     * @param typeRef
     * @param <T>
     * @return
     */
    public <T> T get(String key, TypeReference<T> typeRef) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        return redisTemplate.execute((RedisCallback<T>) connection -> JsonUtil.read(connection.get(key.getBytes(StandardCharsets.UTF_8)), typeRef));
    }

    /**
     * same as set
     *
     * @param key
     * @param obj
     */
    public void set(String key, Object obj) {
        if (obj == null) {
            return;
        }
        redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(StandardCharsets.UTF_8), JsonUtil.writeBytes(obj)));
    }

    /**
     * same as set
     *
     * @param channel
     * @param obj
     */
    public void send(String channel, Object obj) {
        if (obj == null) {
            return;
        }
        redisTemplate.convertAndSend(channel,obj);
    }

    /**
     * same as set
     *
     * @param key
     * @param obj
     */
    public void set(String key, Object obj, long expireMillisecond) {
        if (obj == null) {
            return;
        }
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            final byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
            connection.set(bytes, JsonUtil.writeBytes(obj));
            return connection.pExpire(bytes, expireMillisecond);
        });
    }

    /**
     * same as hGet
     *
     * @param key
     * @param hashKey
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T hGet(String key, String hashKey, Class<T> clazz) {
        return redisTemplate
                .execute((RedisCallback<T>) connection -> JsonUtil.read(connection.hGet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8)), clazz));
    }

    /**
     * 获取多个key的value
     *
     * @param key
     * @param hashKeys
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> hMGet(String key, Collection<String> hashKeys, Class<T> clazz) {
        return redisTemplate
                .execute((RedisCallback<List<T>>) connection -> {
                    final byte[][] collect = Arrays.stream(hashKeys.toArray(new String[0])).map(String::getBytes).collect(Collectors.toList()).toArray(new byte[0][0]);
                    final List<byte[]> bytes = connection.hMGet(key.getBytes(StandardCharsets.UTF_8), collect);
                    if (CollUtil.isEmpty(bytes)) {
                        return null;
                    }
                    return bytes.stream().map(a -> JsonUtil.read(a, clazz)).collect(Collectors.toList());
                });
    }

    /**
     * 获取hash表中所有的value值
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> hVals(String key, Class<T> clazz) {
        return redisTemplate
                .execute((RedisCallback<List<T>>) connection -> {
                    List<byte[]> bytes = connection.hVals(key.getBytes(StandardCharsets.UTF_8));
                    if (bytes == null) {
                        return null;
                    }
                    List<T> result = Lists.newArrayListWithCapacity(bytes.size());
                    for (byte[] b : bytes) {
                        result.add(JsonUtil.read(b, clazz));
                    }
                    return result;
                });
    }

    /**
     * 获取hash表中所有的value值
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> List<T> hVals(String key, TypeReference<T> clazz) {
        return redisTemplate
                .execute((RedisCallback<List<T>>) connection -> {
                    List<byte[]> bytes = connection.hVals(key.getBytes(StandardCharsets.UTF_8));
                    if (bytes == null) {
                        return null;
                    }
                    List<T> result = Lists.newArrayListWithCapacity(bytes.size());
                    for (byte[] b : bytes) {
                        result.add(JsonUtil.read(b, clazz));
                    }
                    return result;
                });
    }


    /**
     *@author: RuanShaoKang
     *@date: 2021/8/13
     *@description: hMSet 多个键值对设置到哈希表中
     *@param: [key, valueMap]
     *@return: void
     */
    public void hMSet(String key, Map<String, Object> valueMap) {
        if (valueMap == null) {
            return;
        }
        Map<byte[], byte[]> hashes = new HashMap<>(valueMap.size(),1);
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            hashes.put(entry.getKey().getBytes(StandardCharsets.UTF_8), JsonUtil.writeBytes(entry.getValue()));
        }
        redisTemplate.execute((RedisCallback<Void>)connection -> {
            connection.hMSet(key.getBytes(StandardCharsets.UTF_8), hashes);
            return null;
        });
    }
    /**
     * 获取hash所有key值和所有的value值
     *
     * @param key
     * @return
     */
    public <T, V> Map<T, V> hGetAll(String key, TypeReference<T> keyType, TypeReference<V> valueType) {
        return redisTemplate
                .execute((RedisCallback<Map<T, V>>) connection -> {
                    Map<byte[], byte[]> obj = connection.hGetAll(key.getBytes(StandardCharsets.UTF_8));
                    if (obj == null) {
                        return null;
                    }
                    Map<T, V> ans = new HashMap<>(obj.size(), 1);
                    for (Map.Entry<byte[], byte[]> entry : obj.entrySet()) {
                        ans.put(JsonUtil.read(entry.getKey(), keyType), JsonUtil.readList(entry.getValue(), valueType));
                    }
                    return ans;
                });
    }

    public <V> Map<String, V> hGetAll(String key, TypeReference<V> valueType) {
        return redisTemplate
                .execute((RedisCallback<Map<String, V>>) connection -> {
                    Map<byte[], byte[]> obj = connection.hGetAll(key.getBytes(StandardCharsets.UTF_8));
                    if (obj == null) {
                        return null;
                    }
                    Map<String, V> ans = new HashMap<>(obj.size(), 1);
                    for (Map.Entry<byte[], byte[]> entry : obj.entrySet()) {
                        ans.put(new String(entry.getKey()), JsonUtil.readList(entry.getValue(), valueType));
                    }
                    return ans;
                });
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        return redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.del(key.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 删除hash中的key
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Long hDel(String key, String hashKey) {
        return redisTemplate
                .execute((RedisCallback<Long>) connection -> connection.hDel(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8)));
    }


    public void hSet(String key, String hashKey, Object obj) {
        if (obj == null) {
            return;
        }
        redisTemplate
                .execute((RedisCallback<Boolean>) connection -> connection.hSet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8), JsonUtil.writeBytes(obj)));
    }

    public void hSet(String key, String hashKey, Object obj, long expireMillisecond) {
        if (obj == null) {
            return;
        }
        redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            final byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
            final byte[] hashKeyBytes = hashKey.getBytes(StandardCharsets.UTF_8);
            connection.hSet(bytes, hashKeyBytes, JsonUtil.writeBytes(obj));
            return connection.pExpire(bytes, expireMillisecond);
        });
    }


    /**
     * same as hGet
     *
     * @param key
     * @param hashKey
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T hGet(String key, String hashKey, TypeReference<T> clazz) {
        return redisTemplate
                .execute((RedisCallback<T>) connection -> JsonUtil.read(connection.hGet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8)), clazz));
    }
}