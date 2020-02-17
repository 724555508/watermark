package com.Yang.commom.utils.db;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.Yang.util.IsNull;
import com.Yang.util.SerizlizeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisUtil {

	
	
	
    RedisTemplate<String, String> redisTemplate;
    
    @Autowired
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
    	//设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		this.redisTemplate = redisTemplate;
	}
    

    /**
     * 键值对设值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public <K, V> Boolean set(K key, V value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
        	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize((String) key), serializer.serialize((String) value));
            return true;
        });
    }

    /**
     * 键值对设值和有效时间
     *
     * @param key   键
     * @param time  有效时间(单位：秒)
     * @return
     */
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }
    /**
     * 
     * @param key
     * @param value
     * @param expire
     * @return
     */
    public <K, V> Boolean setex(K key, V value,long expire) {
    	if(this.set(key, value)) {
    		this.expire(key.toString(), expire);
    		return true;
    	}
    	return false;
    }

    /**
     * 查询键值对
     *
     * @param key           键
     * @param typeReference 返回类型
     * @param <K>           键类型
     * @param <R>           返回类型
     * @return
     */
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }
    
    public long getTtl(final String key) {
    	long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long value = connection.ttl(serializer.serialize(key));
                return value;
            }
        });
        return result;
    }
    
    public Set<String> keys(final String key) {
    	Set<String> result = redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Set<byte[]> value = connection.keys(serializer.serialize(key));
                Set<String> reSet = new HashSet<>();
                for (byte[] bt : value) {
                	reSet.add(serializer.deserialize(bt));
				}
                return reSet;
            }
        });
        return result;
    }
    /**
     * 删除
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(key.getBytes());
                return true;
            }
        });
        return result;
    }
    
    public <K, V> Boolean setObject(K key, Object value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
//            connection.set(JSON.toJSONBytes(key), JSON.toJSONBytes(value));
        	RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.set(serializer.serialize((String) key), SerizlizeUtil.serialize(value));
            return true;
        });
    }
    
    public Object getObject(final String key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return IsNull.isNullOrEmpty(value) ?  null : SerizlizeUtil.unserizlize(value);
            }
        });
        return result;
    }

    public <K, V> Boolean setObjectex(K key, Object value,long expire) {
    	if(this.setObject(key, value)) {
    		this.expire(key.toString(), expire);
    		return true;
    	}
    	return false;
    }
    
    /**
     * 存储Hash结构数据(批量)
     *
     * @param outerKey 外键
     * @param map      内键-内值
     * @return
     */
    public <O, I, V> Boolean hSetMap(O outerKey, Map<I, V> map) {
        if (map == null || map.isEmpty()) return false;
        Map<byte[], byte[]> byteMap = new HashMap<>();
        map.forEach((innerKey, innerValue) -> byteMap.put(JSON.toJSONBytes(innerKey), JSON.toJSONBytes(innerValue)));
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.hMSet(JSON.toJSONBytes(outerKey), byteMap);
            return true;
        });
    }

    /**
     * 存储Hash结构数据
     *
     * @param outerKey   外键
     * @param innerKey   内键
     * @param innerValue 值
     * @return
     */
    public <O, I, V> Boolean hSet(O outerKey, I innerKey, V innerValue) {
        Map<I, V> map = new HashMap<>();
        map.put(innerKey, innerValue);
        return this.hSetMap(outerKey, map);
    }

    /**
     * 获取Hash结构Map集合，内键和内值键值对封装成Map集合
     *
     * @param outerKey       外键
     * @param innerKeyType   内键类型
     * @param innerValueType 值类型
     * @return
     */
    public <O, I, V> Map<I, V> hGetMap(O outerKey, TypeReference<I> innerKeyType, TypeReference<V> innerValueType) {
        Map<byte[], byte[]> redisMap = redisTemplate.execute
                ((RedisCallback<Map<byte[], byte[]>>) connection -> connection.hGetAll(JSON.toJSONBytes(outerKey)));
        if (redisMap == null) return null;
        Map<I, V> resultMap = new HashMap<>();
        redisMap.forEach((key, value) -> resultMap.put(JSONObject.parseObject
                (new String(key), innerKeyType), JSONObject.parseObject(new String(value), innerValueType)));
        return resultMap;
    }

    /**
     * 查询Hash结构的值
     *
     * @param outerKey      外键
     * @param innerKey      内键
     * @param typeReference 值类型
     * @return
     */
    public <O, I, V> V hGet(O outerKey, I innerKey, TypeReference<V> typeReference) {
        byte[] redisResult = redisTemplate.execute((RedisCallback<byte[]>)
                connection -> connection.hGet(JSON.toJSONBytes(outerKey), JSON.toJSONBytes(innerKey)));
        if (redisResult == null) return null;
        return JSONObject.parseObject(new String(redisResult), typeReference);

    }

    /**
     * 删除键值对
     *
     * @param keys 键
     * @return
     */
    public <K> Long del(List<K> keys) {
        if (keys == null || keys.isEmpty()) return 0L;
        byte[][] keyBytes = new byte[keys.size()][];
        int index = 0;
        for (K key : keys) {
            keyBytes[index] = JSON.toJSONBytes(key);
            index++;
        }
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keyBytes));
    }

    /**
     * 删除Hash结构内键和值
     *
     * @param outerKey  外键
     * @param innerKeys 内键
     * @return
     */
    public <O, I> Long hDel(O outerKey, List<I> innerKeys) {
        if (innerKeys == null || innerKeys.isEmpty()) return 0L;
        byte[][] innerKeyBytes = new byte[innerKeys.size()][];
        int index = 0;
        for (I key : innerKeys) {
            innerKeyBytes[index] = JSON.toJSONBytes(key);
            index++;
        }
        return redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.hDel(JSON.toJSONBytes(outerKey), innerKeyBytes));
    }
    
    public Long pList(String key, Collection<String> values){
    	return redisTemplate.opsForList().leftPushAll(key, values);
    }
    
    public Collection<String> hList(String key,int start,int end){
    	return redisTemplate.opsForList().range(key, start, end);
    }
}
