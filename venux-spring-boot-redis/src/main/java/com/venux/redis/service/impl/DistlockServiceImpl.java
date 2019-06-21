package com.venux.redis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venux.redis.annotation.CheckParam;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dao.RedisLock;
import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.dto.param.AddLockApiParam;
import com.venux.redis.dto.param.UnlockApiParam;
import com.venux.redis.exception.BusinessException;
import com.venux.redis.service.DistlockService;
import com.venux.redis.utils.ZredisUtils;

/** 
 * @ClassName: StringRedisService 
 * @Description: Redis封装操作工具类
 * @author liuzy
 * @date 2018年9月13日 下午4:15:25  
 */
@Service
public class DistlockServiceImpl implements DistlockService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisServiceDao redisServiceDao;
	
	@Override
	@CheckParam
	public void addLock(AddLockApiParam<String> addLockApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(addLockApiParam.getKey(), ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_STRING);
		if(addLockApiParam.getExpireSeconds() <= 0){
			addLockApiParam.setExpireSeconds(ZredisConstant.DISTLOCK_EXPIRESECONDS);
		}
		RedisLock redisLock = redisServiceDao.getLock(builderKey, addLockApiParam.getRequestId(), addLockApiParam.getExpireSeconds());
		if(redisLock == null){
			throw new BusinessException("锁已存在");
		}
	}
	
	@Override
	@CheckParam
	public void addLockRetry(AddLockApiParam<String> addLockApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(addLockApiParam.getKey(), ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_STRING);
		if(addLockApiParam.getExpireSeconds() <= 0){
			addLockApiParam.setExpireSeconds(ZredisConstant.DISTLOCK_EXPIRESECONDS);
		}
		RedisLock redisLock = redisServiceDao.getLock(builderKey, addLockApiParam.getRequestId(), addLockApiParam.getExpireSeconds(),ZredisConstant.DISTLOCK_MAX_RETRY_TIMES,ZredisConstant.DISTLOCK_RETRY_INTERVAL_TIME_MILLIS);
		if(redisLock == null){
			throw new BusinessException("锁已存在");
		}
	}

	@Override
	@CheckParam
	public void unlock(UnlockApiParam<String> unlockApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(unlockApiParam.getKey(), ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_STRING);
		redisServiceDao.unlock(builderKey, unlockApiParam.getRequestId());
	}

	@Override
	@CheckParam
	public void addLocks(AddLockApiParam<String[]> addLockApiParams) throws Exception {
		String[] keys = addLockApiParams.getKey();
		List<String> lockList = new ArrayList<String>();
		if(addLockApiParams.getExpireSeconds() <= 0){
			addLockApiParams.setExpireSeconds(ZredisConstant.DISTLOCK_EXPIRESECONDS);
		}
		for (String key : keys) {
			String builderKey = ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_STRING);
			RedisLock redisLock = redisServiceDao.getLock(builderKey, addLockApiParams.getRequestId(), addLockApiParams.getExpireSeconds());
			if(redisLock == null){
				//如果加锁Key中存在失败的情况，则将之前加锁的数据全部清除
				if (lockList.size() > 0) {
					UnlockApiParam<String[]> unlockApiParam = new UnlockApiParam<String[]>();
					unlockApiParam.setKey(lockList.toArray(new String[lockList.size()]));
					unlockApiParam.setRequestId(addLockApiParams.getRequestId());
					this.unlocks(unlockApiParam);
				}
				throw new BusinessException("部分锁已存在");
			}
			lockList.add(key);
		}
	}
	
	@Override
	@CheckParam
	public void unlocks(UnlockApiParam<String[]> unlockApiParam) throws Exception {
		String[] keys = unlockApiParam.getKey();
		String[] keyArray = new String[keys.length];
		for (int i = 0; i < keys.length; i++) {
			keyArray[i] = ZredisUtils.keyBuilder(keys[i], ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_STRING);
			redisServiceDao.unlock(keyArray[i], unlockApiParam.getRequestId());
		}
	}
	
}
