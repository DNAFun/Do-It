package com.xml.common.util.cache.redis;

import com.xml.common.util.cache.redis.constants.RedisConstants;
import com.xml.common.util.cache.redis.exception.DoItRedisException;
import com.xml.common.util.cache.redis.exception.RedisExceptionCode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 操作Redis
 *
 * @author XMINGL
 * @since 1.0.0
 */
@Slf4j
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置超时时间
     *
     * @param key  键
     * @param time 超时时间（秒）
     * @return 操作结果
     */
    public boolean expire(String key, long time) {
        return this.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * @param key  键
     * @param time 超时时间
     * @param unit 时间单位
     * @return 操作结果
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        if (time > 0) {
            try {
                return Boolean.TRUE.equals(redisTemplate.expire(key, time, unit));
            } catch (Exception e) {
                log.error(RedisConstants.REDIS_EXPIRE_ERROR, e);
            }
        }
        return false;
    }

    /**
     * 获取超时时间（秒）
     *
     * @param key 键
     * @return 超时时间（秒）
     */
    public Long getExpireSecond(String key) {
        return this.getExpireSecond(key, TimeUnit.SECONDS);
    }

    /**
     * 按照时间单位获取超时时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return 超时时间
     */
    public Long getExpireSecond(String key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_CHECK_KEY_ERROR, e);
        }
        return false;
    }

    /**
     * 删除缓存
     *
     * @param keys 键
     */
    public void deleteByKey(String... keys) {
        if (Objects.nonNull(keys) && keys.length > 0) {
            redisTemplate.delete(Arrays.asList(keys));
        }
    }

    /**
     * 获取 str
     *
     * @param key 键
     * @return 值
     */
    public Object getString(String key) {
        return Objects.isNull(key) ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 添加 str
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public boolean setString(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }

    }

    /**
     * 添加 str 并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间（秒） 小于等于0时为无限期
     * @return 结果
     */
    public boolean setString(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                return true;
            } else {
                return setString(key, value);
            }
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 递增因数
     * @return 结果
     */
    public Long incrementString(String key, long delta) {
        if (delta <= 0) {
            throw new DoItRedisException(RedisExceptionCode.SYSTEM_501.getCode(), "递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 递减因数
     * @return 结果
     */
    public Long decrementString(String key, long delta) {
        if (delta >= 0) {
            throw new DoItRedisException(RedisExceptionCode.SYSTEM_501.getCode(), "递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 设置 map
     *
     * @param key   键
     * @param hash  项
     * @param value 值
     * @return 结果
     */
    public boolean setMap(String key, String hash, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hash, value);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
        }
        return false;
    }

    /**
     * 设置 map 并设置超时时间（秒）
     *
     * @param key   键
     * @param hash  项
     * @param value 值
     * @param time  超时时间
     * @return 结果
     */
    public boolean setMap(String key, String hash, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, hash, value);
            return this.expire(key, time);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
        }
        return false;
    }

    /**
     * 获取map
     *
     * @param key 键
     * @return MAP
     */
    public Map<Object, Object> getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取map中key对应的value
     *
     * @param key  键
     * @param hash 项
     * @return 值
     */
    public Object getMap(String key, String hash) {
        return redisTemplate.opsForHash().get(key, hash);
    }

    /**
     * 添加MAP
     *
     * @param key 键
     * @param map MAP
     * @return 结果
     */
    public boolean setMap(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
        }
        return false;
    }

    /**
     * 添加MAP并设置超时时间（秒）
     *
     * @param key  键
     * @param map  MAP
     * @param time 时间（秒）
     * @return 结果
     */
    public boolean setMap(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return expire(key, time);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
        }
        return false;
    }

    /**
     * 删除
     *
     * @param key  键
     * @param hash hash
     */
    public void deleteMap(String key, Object... hash) {
        redisTemplate.opsForHash().delete(key, hash);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键
     * @param hash 项
     * @return 结果
     */
    public boolean hasMapKey(String key, String hash) {
        return redisTemplate.opsForHash().hasKey(key, hash);
    }

    /**
     * 递增
     *
     * @param key   键
     * @param hash  项
     * @param delta 递增因数
     * @return 结果
     */
    public Double incrementMap(String key, String hash, double delta) {
        if (delta <= 0) {
            throw new DoItRedisException(RedisExceptionCode.SYSTEM_501.getCode(), "递增因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hash, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param hash  项
     * @param delta 递减因数
     * @return 结果
     */
    public Double decrementMap(String key, String hash, double delta) {
        if (delta >= 0) {
            throw new DoItRedisException(RedisExceptionCode.SYSTEM_501.getCode(), "递减因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hash, -delta);
    }

    /**
     * 获取Set
     *
     * @param key 键
     * @return 结果
     */
    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_GET_ERROR, e);
            return null;
        }
    }

    /**
     * 查询set是否存在value
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    public boolean hasSetValue(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_CHECK_KEY_ERROR, e);
            return false;
        }
    }

    /**
     * 增加set
     *
     * @param key    键
     * @param values 值
     * @return 成功数
     */
    public Long setSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return 0L;
        }
    }

    /**
     * 增加set并设置超时时间
     *
     * @param key    键
     * @param time   时间
     * @param values 值
     * @return 成功数
     */
    public Long setSet(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            expire(key, time);
            return count;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return 0L;
        }
    }

    /**
     * 获取set的大小
     *
     * @param key 键
     * @return size
     */
    public Long getSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_GET_ERROR, e);
            return 0L;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long deleteSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_DELETE_ERROR);
            return 0L;
        }
    }

    /**
     * 保存list并设置超时时间（秒）
     *
     * @param key   键
     * @param value 值
     * @param time  超时时间
     * @return 结果
     */
    public boolean setList(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            expire(key, time);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    /**
     * 保存list
     *
     * @param key   键
     * @param value 值
     * @return 结果
     */
    public boolean setList(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    /**
     * 保存list并设置超时时间（秒）
     *
     * @param key   键
     * @param value 值
     * @param time  超时时间
     * @return 结果
     */
    public boolean listSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    /**
     * 获取list<br/>
     * 0 到 -1代表所有值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return list
     */
    public List<Object> getList(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_GET_ERROR, e);
            return null;
        }
    }

    /**
     * 获取list的大小
     *
     * @param key 键
     * @return size
     */
    public Long getListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_GET_ERROR, e);
            return 0L;
        }
    }

    /**
     * 通过索引获取list中的值<br/>
     * <li>>=0 : 从第一个元素向后</li>
     * <li> <0 : 从最后一个元素向前</li>
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    public Object getListItemByIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_GET_ERROR, e);
            return null;
        }
    }

    /**
     * 将item放入list
     *
     * @param key  键
     * @param item 值
     * @return 结果
     */
    public boolean setListItem(String key, Object item) {
        try {
            redisTemplate.opsForList().rightPush(key, item);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param item  值
     * @return 结果
     */
    public boolean updateListItemByIndex(String key, long index, Object item) {
        try {
            redisTemplate.opsForList().set(key, index, item);
            return true;
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_SET_ERROR, e);
            return false;
        }
    }

    public Long deleteListItem(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            log.error(RedisConstants.REDIS_DELETE_ERROR, e);
            return 0L;
        }
    }

}
