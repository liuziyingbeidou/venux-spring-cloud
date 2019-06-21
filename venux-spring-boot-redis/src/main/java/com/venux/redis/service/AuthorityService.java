package com.venux.redis.service;

import java.util.Set;

import com.venux.redis.dto.param.AuthApiParam;

/** 
 * @Title: ZJS 
 * @ClassName: ZjsRedisOperations 
 * @Description: Redis操作模板类接口
 * @author liuzy
 * @date 2018年9月13日 下午4:17:35  
 */
public interface AuthorityService {

	/**
	 * 字符串String存放接口
	 * @param authApiParam
	 * @throws Exception
	 * @return void
	 */
	public void addAuth(AuthApiParam<String, String> authApiParam) throws Exception;
	
	/**
	 * 根据key查询字符串接口
	 * @param key
	 * @throws Exception
	 * @return String
	 */
	public String getAuth(String key) throws Exception;
	
	/**
	 * 根据key删除字符串String接口
	 * @param key
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean delAuth(String key) throws Exception;
	
	/**
	 * 集合Set存放接口
	 * @param authApiParam
	 * @throws Exception
	 * @return long
	 */
	public long addAuthToSet(AuthApiParam<String, String[]> authApiParam) throws Exception;
	
	/**
	 * 根据key-value删除集合Set接口
	 * @param authApiParam
	 * @throws Exception
	 * @return long
	 */
	public long delAuthPropertyFromSet(AuthApiParam<String, String> authApiParam) throws Exception;
	
	/**
	 * 根据key-values删除集合Set接口
	 * @param authApiParam
	 * @throws Exception
	 * @return long
	 */
	public long delAuthPropertysFromSet(AuthApiParam<String, String[]> authApiParam) throws Exception;
	
	/**
	 * 根据key删除集合Set接口
	 * @param key
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean delAuthFromSet(String key) throws Exception;
	
	/**
	 * 根据key查询字符串接口
	 * @param key
	 * @throws Exception
	 * @return Set<String>
	 */
	public Set<String> getAuthFromSet(String key) throws Exception;
	
}
