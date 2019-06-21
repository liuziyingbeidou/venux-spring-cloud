package com.venux.redis.service;

import com.venux.redis.dto.param.BaseApiParam;

/** 
 * @ClassName: ZjsRedisOperations 
 * @Description: Redis操作模板类接口
 * @author liuzy
 * @date 2018年9月13日 下午4:17:35  
 */
public interface BaseService {

	/**
	 * 字符串String存放接口
	 * @param authApiParam
	 * @throws Exception
	 * @return void
	 */
	public void add(BaseApiParam<String, String> baseApiParam) throws Exception;
	
	/**
	 * 根据key查询字符串接口
	 * @param authApiParam
	 * @throws Exception
	 * @return String
	 */
	public String get(String key) throws Exception;
	
	/**
	 * 根据key删除字符串String接口
	 * @param key
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean del(String key) throws Exception;
	
	/**
	 * 按delta递增
	 * @param key
	 * @throws Exception
	 * @return Double
	 */
	public Double incrBy(String key, long delta, long expireSeconds) throws Exception;
	
	/**
	 * 按delta递减
	 * @param key
	 * @throws Exception
	 * @return Double
	 */
	public Double decrBy(String key, long delta, long expireSeconds) throws Exception;
}
