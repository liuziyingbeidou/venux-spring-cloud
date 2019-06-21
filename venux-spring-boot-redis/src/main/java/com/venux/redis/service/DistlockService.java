package com.venux.redis.service;

import com.venux.redis.dto.param.AddLockApiParam;
import com.venux.redis.dto.param.UnlockApiParam;

/** 
 * @ClassName: ZjsRedisOperations 
 * @Description: Redis操作模板类接口
 * @author liuzy
 * @date 2018年9月13日 下午4:17:35  
 */
public interface DistlockService {

	/**
	 * @param addLockApiParam
	 * @param key 要锁定的key
     * @param requestId 请求标识
     * @param expireSeconds key的失效时间
	 * @return String 1000-成功，2000-失败
	 */
	public void addLock(AddLockApiParam<String> addLockApiParam) throws Exception;
	
	/**
	 * @param addLockApiParam
	 * @param key 要锁定的key
     * @param requestId 请求标识
     * @param expireSeconds key的失效时间
	 * @return String 1000-成功，2000-失败
	 */
	public void addLocks(AddLockApiParam<String[]> addLockApiParams) throws Exception;
	
	/**
	 * 在获取失败时会自动重试
	 * @param addLockApiParam
	 * @param key 要锁定的key
     * @param requestId 请求标识
     * @param expireSeconds key的失效时间
	 * @return String 1000-成功，2000-失败
	 */
	public void addLockRetry(AddLockApiParam<String> addLockApiParam) throws Exception;
	
	/**
	 * @param unlockApiParam
	 * @param key 要锁定的key
     * @param requestId 请求标识
	 */
	public void unlock(UnlockApiParam<String> unlockApiParam) throws Exception;
	
	/**
	 * @param unlockApiParam
	 */
	public void unlocks(UnlockApiParam<String[]> unlockApiParam) throws Exception;
	
}
