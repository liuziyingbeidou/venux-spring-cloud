package com.venux.redis.service;

/** 
 * @ClassName: ZjsRedisOperations 
 * @Description: Redis操作模板类接口
 * @author liuzy
 * @date 2018年9月13日 下午4:17:35  
 */
public interface PubSubService{
	
	/**
	 * 发布
	 * @param channel
	 * @param message
	 * @throws Exception
	 * @return boolean
	 */
	public boolean publish(String channel, Object message) throws Exception;
	
}
