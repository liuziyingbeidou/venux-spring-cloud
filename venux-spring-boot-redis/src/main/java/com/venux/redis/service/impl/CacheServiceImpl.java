package com.venux.redis.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.dto.param.PubSubApiParam;
import com.venux.redis.exception.BusinessException;
import com.venux.redis.service.CacheService;
import com.venux.redis.service.PubSubService;
import com.venux.redis.utils.PubUtils;
import com.venux.redis.utils.ZredisUtils;

/** 
 * @ClassName: CacheServiceImpl 
 * @Description: 缓存实现
 * @author liuzy
 * @date 2019年1月14日 上午9:23:24  
 */
@Service
public class CacheServiceImpl implements CacheService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisServiceDao redisServiceDao;
	@Autowired
	private PubSubService pubSubService;
	
	/**
	 * 存储类型
	 */
	private String cacheTypeStr = ZredisConstant.ZREDIS_TYPE_STRING ;
	private String cacheTypeZSet = ZredisConstant.ZREDIS_TYPE_ZSET;
	
	private String getNewKey(String key, String type) throws Exception{
		if(PubUtils.isNull(key)){
			throw new BusinessException("传入的键key为空！");
		}
		return ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.CACHE, type);
	}
	
	public void add(String key, String value, long expireSeconds) throws Exception{
		String builderKey = getNewKey(key, cacheTypeStr);
		if(expireSeconds == 0){
			expireSeconds = ZredisConstant.CACHE_EXPIRESECONDS;
		}else if(expireSeconds < 0){
			redisServiceDao.set(builderKey, value);
			return;
		}
		redisServiceDao.set(builderKey, value, expireSeconds);
	}
	
	@Override
	public boolean addPublish(String key, String value, long expireSeconds, String producer) throws Exception {
		//添加到redis
		this.add(key, value, expireSeconds);
		//添加成功，发布
		PubSubApiParam<String, String> pubSubApiParam = new PubSubApiParam<String, String>();
		pubSubApiParam.setKey(key.trim());
		pubSubApiParam.setProducer(producer);
		return pubSubService.publish(ZredisUtils.ZredisPrefix.PUBSUB.toString(), JSON.toJSONString(pubSubApiParam));
	}

	@Override
	public Boolean del(String key) throws Exception {
		String builderKey = getNewKey(key, cacheTypeStr);
		return redisServiceDao.remove(builderKey);
	}
	
	@Override
	public String get(String key) throws Exception {
		String builderKey = getNewKey(key, cacheTypeStr);
		return redisServiceDao.get(builderKey);
	}

	@Override
	public Boolean zAdd(String key, String value, double score,long expireSeconds) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		Boolean result = false;
		if(expireSeconds == 0){
			expireSeconds = ZredisConstant.CACHE_EXPIRESECONDS;
			result = redisServiceDao.zAdd(builderKey, value, score);
			this.setExpire(builderKey, expireSeconds);
		}else if(expireSeconds < 0){
			result = redisServiceDao.zAdd(builderKey, value, score);
		}
		return result;
	}

	@Override
	public Set<String> zRange(String key, long start, long end) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.zRange(builderKey, start, end);
	}

	@Override
	public Set<TypedTuple<String>> zRangeWithScores(String key, long start, long end) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.zRangeWithScores(builderKey, start, end);
	}
	
	@Override
	public Double zScore(String key, Object value) throws Exception{
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.zScore(builderKey, value);
	}
	
	@Override
	public Long zRemove(String key, Object... values) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.zRemove(builderKey, values);
	}

	@Override
	public Double zIncrby(String key, String value, double delta) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.zIncrby(builderKey, value, delta);
	}

	@Override
	public Boolean setExpire(String key, long seconds) throws Exception {
		String builderKey = getNewKey(key, cacheTypeZSet);
		return redisServiceDao.setLiveTime(builderKey, seconds);
	}

	@Override
	public Boolean addIfAbsent(String key, String value, long expireSeconds) throws Exception {
		String builderKey = getNewKey(key, cacheTypeStr);
		return redisServiceDao.setNx(builderKey, value, expireSeconds);
	}
	
}
