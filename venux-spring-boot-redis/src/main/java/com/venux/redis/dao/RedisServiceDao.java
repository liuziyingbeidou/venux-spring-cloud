package com.venux.redis.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import com.venux.redis.constant.LuaScriptConstant;
import com.venux.redis.constant.ZredisConstant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * @ClassName: StringRedisService
 * @Description: Redis封装操作工具类
 * @author liuzy
 * @date 2018年9月13日 下午4:15:25
 */
@Repository
public class RedisServiceDao{

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> opsForValue;
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> opsForHash;
	@Resource(name = "stringRedisTemplate")
	private ListOperations<String, String> opsForList;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> opsForSet;
	@Resource(name = "stringRedisTemplate")
	private ZSetOperations<String, String> opsForZSet;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
     * 将key 的值设为value ，当且仅当key 不存在，等效于 SETNX。
     */
    public static final String NX = "NX";
 
    /**
     * seconds — 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
     */
    public static final String EX = "EX";
    
    public static final String LOCK_SUCCESS = "OK";
	
	/**
	 * 存放String类型数据（不设置expire存活时间）
	 */
	public void set(final String key, String value) throws Exception {
		opsForValue.set(key, value);
	}

	/**
	 * 同时设置一个或多个key-value对
	 */
	public void mset(final Map<String, String> keyvalues) throws Exception {
		opsForValue.multiSet(keyvalues);
	}

	/**
	 * 存放String类型数据（设置expire存活时间）
	 */
	public void set(final String key, String value, long expiredSecond) throws Exception {
		opsForValue.set(key, value, expiredSecond, TimeUnit.SECONDS);
	}
	
	/**
	 * 存放String类型数据，key存在不更新；不存在设值
	 */
	public Boolean setNx(final String key, String value) throws Exception{
		return opsForValue.setIfAbsent(key, value);
	}
	
	/**
	 * 存放String类型数据（设置expire存活时间）
	 */
	public Boolean setNx(final String key, final String value, final long expiredSecond) throws Exception {
		if (expiredSecond > 0) {
    		Object incrementValue = stringRedisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                	Object nativeConnection = connection.getNativeConnection();
                	if(nativeConnection instanceof Jedis){
                		return ((Jedis) nativeConnection).eval(LuaScriptConstant.LUA_SETNX, 1 , new String[]{key, String.valueOf(value), String.valueOf(expiredSecond)});
                    }else{
                    	return ((JedisCluster) nativeConnection).eval(LuaScriptConstant.LUA_SETNX, 1 , new String[]{key, String.valueOf(value), String.valueOf(expiredSecond)});
                    }
                }
            });
    		return ZredisConstant.REDIS_RETURN_CODE_0 == Long.valueOf(incrementValue.toString()) ? Boolean.FALSE : Boolean.TRUE;
    	}else{
    		return this.setNx(key, value);
    	}
	}

	/**
	 * 取String类型数据
	 */
	public String get(final String key) throws Exception {
		return opsForValue.get(key);
	}

	/**
	 * 根据多个key读取，返回List Value
	 */
	public List<String> mget(final Set<String> keys) throws Exception {
		return opsForValue.multiGet(keys);
	}

	/**
	 * 向左边(头)追加list
	 */
	public long pushLeftList(String key, String... values) throws Exception {
		return opsForList.leftPushAll(key, values);
	}

	/**
	 * 向右边(尾)追加list
	 */
	public long pushRightList(String key, String... values) throws Exception {
		return opsForList.rightPushAll(key, values);
	}

	/**
	 * 存放新的list，覆盖原有Key值list
	 */
	public long putList(String key, String... list) throws Exception {
		return opsForList.rightPushAll(key, list);
	}

	/**
	 * 获得自定义长度的list集合
	 */
	public List<String> getList(String key, int start, int end) throws Exception {
		return opsForList.range(key, start, end);
	}

	/**
	 * 获得整个list集合
	 */
	public List<String> getList(String key) throws Exception {
		return getList(key, 0, -1);
	}

	/**
	 * 删除先进入的一个value
	 */
	public long listRemoveOneResit(String key, String value) throws Exception {
		return opsForList.remove(key, 1, value);
	}

	/**
	 * 删除所有的value
	 */
	public long listRemoveAllResit(String key, String value) throws Exception {
		return opsForList.remove(key, 0, value);
	}

	/**
	 * 判断redis缓存中是否有对应的key
	 */
	public Boolean exists(final String key) throws Exception {
		return stringRedisTemplate.hasKey(key);
	}

	/**
	 * redis根据key删除对应的value
	 */
	public Boolean remove(final String key) throws Exception {
		Boolean result = false;
		if (exists(key)) {
			stringRedisTemplate.delete(key);
			result = true;
		}
		return result;
	}

	/**
	 * redis根据keys批量删除对应的value
	 */
	public void remove(final String... keys) throws Exception {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 移除指定key的存活时间,该key会变成持久化而非删除
	 */
	public Boolean removeLiveTime(String key) throws Exception {
		return stringRedisTemplate.persist(key);
	}

	/**
	 * 设置生存时间
	 */
	public Boolean setLiveTime(String key, long seconds) throws Exception {
		return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 返回KEY的剩余生存时间
	 */
	public long getTimeToLive(String key) throws Exception {
		return stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 向名称为key的hash中添加元素Key Value
	 */
	public void hset(String key, String hashKey, String value) throws Exception {
		opsForHash.put(key, hashKey, value);
	}

	/**
	 * 取名称key的哈希表中key的值
	 */
	public String hGet(String key, String hashKey) throws Exception {
		return String.valueOf(opsForHash.get(key, hashKey));
	}

	/**
	 * 同时将多个field - value(域-值)对设置到 名称为 key的哈希表
	 */
	public void hmSet(String key, Map<String, String> map) throws Exception {
		opsForHash.putAll(key, map);
	}

	/**
	 * 返回名称为key的hash中 一个或者多个key 对应的value
	 */
	public List<String> hmGet(String key, Set<String> hashKeys) throws Exception {
		return opsForHash.multiGet(key, hashKeys);
	}

	/**
	 * 删除名称为key的hash中键为key的域,可删除多个
	 */
	public void hDel(String key, String... hashKeys) throws Exception {
		opsForHash.delete(key, hashKeys);
	}

	/**
	 * 名称为key的hash中是否存在键名字为a的域
	 */
	public boolean hExists(String key, String hashKey) throws Exception {
		return opsForHash.hasKey(key, hashKey);
	}

	/**
	 * 返回名称为key的hash中所有的键（field）及其对应的value
	 */
	public Map<String, String> hGetAll(String key) throws Exception {
		return opsForHash.entries(key);
	}

	/**
	 * 返回名称为 key 的hash中所有键
	 */
	public Set<String> hKeys(String key) throws Exception {
		return opsForHash.keys(key);
	}

	/**
	 * 返回名称为key的hash中所有键对应的value
	 */
	public List<String> hValues(String key) throws Exception {
		return opsForHash.values(key);
	}

	/**
	 * 返回名称为key的hash中键的个数
	 */
	public long hLen(String key) throws Exception {
		return opsForHash.size(key);
	}

	/**
	 * 向名称为key的set中添加元素value,如果value存在，不写入
	 */
	public long sAdd(String key, String... values) throws Exception {
		return opsForSet.add(key, values);
	}

	/**
	 * 清空Key值set，并存放新的values
	 */
	public long sSet(String key, String... values) throws Exception {
		long result = 0;
		if (remove(key)) {
			result = opsForSet.add(key, values);
		}
		return result;
	}

	/**
	 * 删除名称为key的set中的元素values
	 */
	public long sRemove(String key, Object... values) throws Exception {
		return opsForSet.remove(key, values);
	}

	/**
	 * 返回名称为key的set的所有元素
	 */
	public Set<String> sGetMembers(String key) throws Exception {
		return opsForSet.members(key);
	}

	/**
	 * 判断成员元素是否是集合的成员
	 */
	public Boolean sIsMember(String key, Object value) throws Exception {
		return opsForSet.isMember(key, value);
	}

	/**
	 * 向有序集合添加成员，或者更新已存在成员的分数
	 */
	public Boolean zAdd(String key, String value, double score) throws Exception{
		return opsForZSet.add(key, value, score);
	}
	
	/**
	 * 获取有序集合的成员数
	 */
	public Long zCard(String key) throws Exception{
		return opsForZSet.zCard(key);
	}
	
	/**
	 * 返回有序集中，成员的分数值
	 */
	public Double zScore(String key, Object value) throws Exception{
		return opsForZSet.score(key, value);
	}
	
	/**
	 * 通过索引区间返回有序集合成指定区间内的成员
	 * 
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
	 */
	public Set<String> zRange(String key, long start, long end) throws Exception{
		return opsForZSet.range(key, start, end);
	}
	
	/**
	 * 通过索引区间返回有序集合成指定区间内的成员(带分数)
	 * 
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
	 */
	public Set<TypedTuple<String>> zRangeWithScores(String key, long start, long end) throws Exception{
		return opsForZSet.rangeWithScores(key, start, end);
	}
	
	/**
	 * 移除有序集合中的一个或多个成员，不存在的成员将被忽略。
	 */
	public Long zRemove(String key, Object... values) throws Exception{
		return opsForZSet.remove(key, values);
	}
	
	/**
	 * 有序集合中对指定成员的分数加上增量 increment
	 */
	public Double zIncrby(String key, String value, double delta) throws Exception{
		if(delta == 0){
			delta = ZredisConstant.DELTA_DEFAULT;
		}
		return opsForZSet.incrementScore(key, value, delta);
	}
	
	/**
	 * 存放java对象
	 */
	public <T> void setObject(String key, T t) throws Exception {
		redisTemplate.opsForValue().set(key, t);
	}

	/**
	 * 存放java对象(带过期时间秒)
	 */
	public <T> void setObjectWithExpiredTime(String key, T t, int expiredSecond) throws Exception {
		redisTemplate.opsForValue().set(key, t, expiredSecond, TimeUnit.SECONDS);
	}

	/**
	 * 批量存放java对象
	 */
	public <T> void mSetObject(Map<byte[], byte[]> keysvalues) throws Exception {
		redisTemplate.opsForValue().multiSet(keysvalues);
	}

	/**
	 * 取得java对象
	 */
	public Object getObject(String key) throws Exception {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 批量获取java对象
	 */
	public List<byte[]> mGetObject(Set<byte[]> keys) throws Exception {
		return redisTemplate.opsForValue().multiGet(keys);
	}
	
	/**
	 * 按增量递增
	 * @param key
	 * @param delta 增量
	 * @throws Exception
	 * @return Long
	 */
	public Double incr(String key, double delta) throws Exception{
		return opsForValue.increment(key, delta);
	}
	
	/**
	 * 按增量递减
	 * @param key
	 * @param delta 增量
	 * @throws Exception
	 * @return Long
	 */
	public Double decr(String key, double delta) throws Exception{
		return opsForValue.increment(key, -delta);
	}
	
	/**
	 * 按增量递增
	 * @param key
	 * @param delta 增量
	 * @param defaultExpire 超时时间
	 * @throws Exception
	 * @return Long
	 */
    public Double incrBy(final String key, final  long delta, final long defaultExpire) throws Exception{
    	if (defaultExpire > 0) {
    		Object incrementValue = stringRedisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                	Object nativeConnection = connection.getNativeConnection();
                	if(nativeConnection instanceof Jedis){
                		return ((Jedis) nativeConnection).eval(LuaScriptConstant.LUA_INCRBY, 1 , new String[]{key, String.valueOf(delta), String.valueOf(defaultExpire)});
                    }else{
                    	return ((JedisCluster) nativeConnection).eval(LuaScriptConstant.LUA_INCRBY, 1 , new String[]{key, String.valueOf(delta), String.valueOf(defaultExpire)});
                    }
                }
            });
    		return Double.valueOf(incrementValue.toString());
    	}else{
    		return this.incr(key, delta);
    	}
    }
	
	/**
	 * 按增量递减
	 * @param key
	 * @param delta 增量
	 * @param defaultExpire 超时时间
	 * @throws Exception
	 * @return Long
	 */
    public Double decrBy(final String key, final  long delta, final long defaultExpire) throws Exception{
    	if (defaultExpire > 0) {
    		Object incrementValue = stringRedisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                	Object nativeConnection = connection.getNativeConnection();
                	if(nativeConnection instanceof Jedis){
                		return ((Jedis) nativeConnection).eval(LuaScriptConstant.LUA_INCRBY, 1 , new String[]{key, String.valueOf(-delta), String.valueOf(defaultExpire)});
                    }else{
                    	return ((JedisCluster) nativeConnection).eval(LuaScriptConstant.LUA_INCRBY, 1 , new String[]{key, String.valueOf(-delta), String.valueOf(defaultExpire)});
                    }
                }
            });
    		return Double.valueOf(incrementValue.toString());
    	}else{
    		return this.decr(key, delta);
    	}
    }
    
	/**
	 * 发布
	 * @param channel 频道
	 * @param message 消息
	 * @throws Exception
	 * @return void
	 */
	public void publish(String channel, Object message) throws Exception{
		stringRedisTemplate.convertAndSend(channel, message);
	}

	  /**
     * 获取redis的分布式锁，内部实现使用了redis的setnx。只会尝试一次，如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁.
     * <br/>使用方法：
     * <pre>
     * RedisLock lock = redisServiceDao.getLock(key, uuid,2);
     * if(lock != null){
     *      try {
     *          //lock succeed, do something
     *      }finally {
     *          lock.unlock();
     *      }
     * }
     * </pre>
     * 由于RedisLock实现了AutoCloseable,所以可以使用更简介的使用方法:
     * <pre>
     *  try(RedisLock lock = redisServiceDao.getLock(key,uuid, 2)) {
     *      if (lock != null) {
     *          //lock succeed, do something
     *      }
     *  }
     * </pre>
     * @param key 要锁定的key
     * @param requestId 请求标识
     * @param expireSeconds key的失效时间
     * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
     */
    public RedisLock getLock(final String key, final String requestId, long expireSeconds) throws Exception{
        return getLock(key, requestId, expireSeconds, 0, 0);
    }
 
    /**
     * 获取redis的分布式锁，内部实现使用了redis的setnx。如果锁定失败返回null，如果锁定成功则返回RedisLock对象，调用方需要调用RedisLock.unlock()方法来释放锁
     * 此方法在获取失败时会自动重试指定的次数,由于多次等待会阻塞当前线程，请尽量避免使用此方法</span>
     *
     * @param key 要锁定的key
     * @param requestId 请求标识
     * @param expireSeconds key的失效时间
     * @param maxRetryTimes 最大重试次数,如果获取锁失败，会自动尝试重新获取锁；
     * @param retryIntervalTimeMillis 每次重试之前sleep等待的毫秒数
     * @return 获得的锁对象（如果为null表示获取锁失败），后续可以调用该对象的unlock方法来释放锁.
     */
    public RedisLock getLock(final String key, final String requestId, final long expireSeconds, int maxRetryTimes, long retryIntervalTimeMillis) throws Exception{
        //final String value = key.hashCode()+"";
        int maxTimes = maxRetryTimes + 1;
        for(int i = 0;i < maxTimes; i++) {
            String status = stringRedisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                	Object nativeConnection = connection.getNativeConnection();
                	String status = null;
                	if(nativeConnection instanceof Jedis){
                		status = ((Jedis) nativeConnection).set(key, requestId, NX, EX, expireSeconds);
                    }else{
                    	status = ((JedisCluster) nativeConnection).set(key, requestId, NX, EX, expireSeconds);
                    }
                    return status;
                }
            });
            
            if (LOCK_SUCCESS.equals(status)) {//抢到锁
                return new RedisLockInner(stringRedisTemplate, key, requestId);
            }
            
            if(retryIntervalTimeMillis > 0) {
                try {
                    Thread.sleep(retryIntervalTimeMillis);
                } catch (InterruptedException e) {
                    break;
                }
            }
            if(Thread.currentThread().isInterrupted()){
                break;
            }
        }
 
        return null;
    }
    
    /**
     * @param key 要锁定的key
     * @param requestId 请求标识
     * @return void
     */
    public void unlock(String key, String requestId) throws Exception{
    	final List<String> keys = Collections.singletonList(key);
    	final List<String> args = Collections.singletonList(requestId);
        //stringRedisTemplate.execute(new DefaultRedisScript<>(LuaScriptConstant.LUA_AND_UNLOCK, String.class), keys, requestId);
        stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
            	Object nativeConnection = connection.getNativeConnection();
            	Object status = null;
            	if(nativeConnection instanceof Jedis){
                	status = ((Jedis) nativeConnection).eval(LuaScriptConstant.LUA_AND_UNLOCK, keys,args);
                }else{
            		status = ((JedisCluster) nativeConnection).eval(LuaScriptConstant.LUA_AND_UNLOCK, keys,args);
                } 
                return status;
            }
        });
    }
    
    private class RedisLockInner implements RedisLock{
        private StringRedisTemplate stringRedisTemplate;
        private String key;
        private String expectedValue;
 
        protected RedisLockInner(StringRedisTemplate stringRedisTemplate,String key, String expectedValue){
            this.stringRedisTemplate = stringRedisTemplate;
            this.key = key;
            this.expectedValue = expectedValue;
        }
 
        /**
         * 释放redis分布式锁
         */
        @Override
        public void unlock(){
        	final List<String> keys = Collections.singletonList(key);
        	final List<String> args = Collections.singletonList(expectedValue);
            //stringRedisTemplate.execute(new DefaultRedisScript<>(LuaScriptConstant.LUA_AND_UNLOCK, String.class), keys, expectedValue);
             stringRedisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                	Object nativeConnection = connection.getNativeConnection();
                	Object status = null;
                	if(nativeConnection instanceof Jedis){
                    	status = ((Jedis) nativeConnection).eval(LuaScriptConstant.LUA_AND_UNLOCK, keys,args);
                    }else{
                		status = ((JedisCluster) nativeConnection).eval(LuaScriptConstant.LUA_AND_UNLOCK, keys,args);
                    } 
                    return status;
                }
            });
        }
 
        @Override
        public void close() throws Exception {
            this.unlock();
        }
    }
	
}
