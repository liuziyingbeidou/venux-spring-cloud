package com.venux.redis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venux.redis.annotation.CheckParam;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.dto.param.BaseApiParam;
import com.venux.redis.service.BaseService;
import com.venux.redis.utils.ZredisUtils;
import com.venux.redis.utils.ZredisUtils.ZredisPrefix;

/** 
 * @ClassName: StringRedisService 
 * @Description: Redis封装操作工具类
 * @author liuzy
 * @date 2018年9月13日 下午4:15:25  
 */
@Service
public class BaseServiceImpl implements BaseService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisServiceDao redisServiceDao;
	
	/**
	 * 前缀
	 */
	private ZredisPrefix preBase = ZredisUtils.ZredisPrefix.BASE ;
	/**
	 * 前缀类型
	 */
	private String baseTypeStr = ZredisConstant.ZREDIS_TYPE_STRING ;
	
	
	@Override
	@CheckParam
	public void add(BaseApiParam<String, String> authApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(authApiParam.getKey(), preBase, baseTypeStr);
		redisServiceDao.set(builderKey, authApiParam.getValue());
	}

	@Override
	@CheckParam
	public String get(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, preBase, baseTypeStr);
		return redisServiceDao.get(builderKey);
	}

	@Override
	@CheckParam
	public Boolean del(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, preBase, baseTypeStr);
		return redisServiceDao.remove(builderKey);
	}
	
	@Override
	public Double incrBy(String key, long delta, long expireSeconds) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, preBase, baseTypeStr);
		if(expireSeconds == 0){
			expireSeconds = ZredisConstant.CACHE_EXPIRESECONDS;
		}else if(expireSeconds < 0){
			return redisServiceDao.incr(builderKey, delta);
		}
		return redisServiceDao.incrBy(builderKey, delta, expireSeconds);
	}

	@Override
	public Double decrBy(String key, long delta, long expireSeconds) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, preBase, baseTypeStr);
		if(expireSeconds == 0){
			expireSeconds = ZredisConstant.CACHE_EXPIRESECONDS;
		}else if(expireSeconds < 0){
			return redisServiceDao.decr(builderKey, delta);
		}
		return redisServiceDao.decrBy(builderKey, delta, expireSeconds);
	}
	
}
