package com.venux.redis.service;

import java.util.Set;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/** 
 * @Title: ZJS 
 * @ClassName: ZjsRedisOperations 
 * @Description: Redis操作模板类接口
 * @author liuzy
 * @date 2018年9月13日 下午4:17:35  
 */
public interface CacheService {
	
	/**
	 * 字符串String存放接口
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @throws Exception
	 * @return void
	 */
	public void add(String key, String value, long expireSeconds) throws Exception;
	
	/**
	 * 字符串String存放（存在时不存放）接口
	 * @param key
	 * @param value
	 * @param expireSeconds
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean addIfAbsent(String key, String value, long expireSeconds) throws Exception;
	
	/**
	 * 缓存且发布订阅
	 * @throws Exception
	 * @return boolean
	 */
	public boolean addPublish(String key, String value, long expireSeconds,String producer) throws Exception;
	
	/**
	 * 根据key查询字符串接口
	 * @param key
	 * @throws Exception
	 * @return String
	 */
	public String get(String key) throws Exception;
	
	/**
	 * 根据key删除字符串接口
	 * @param key
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean del(String key) throws Exception;
	
	/**
	 * 向有序集合添加成员，或者更新已存在成员的分数
	 * @param key
	 * @param value
	 * @param score
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean zAdd(String key, String value, double score,long expireSeconds) throws Exception;
	
	/**
	 * 通过索引区间返回有序集合成指定区间内的成员
	 * 
	 * 下标 0开始，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员
	 *  -1 表示最后一个成员， -2 表示倒数第二个成员
	 * @param key
	 * @param start
	 * @param end
	 * @throws Exception
	 * @return Set<String>
	 */
	public Set<String> zRange(String key, long start, long end) throws Exception;
	
	/**
	 * 通过索引区间返回有序集合成指定区间内的成员(带分数)
	 * 
	 * 	 下标 0开始，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员
	 *  -1 表示最后一个成员， -2 表示倒数第二个成员
	 * @param key
	 * @param start
	 * @param end
	 * @throws Exception
	 * @return Set<TypedTuple<String>>
	 */
	public Set<TypedTuple<String>> zRangeWithScores(String key, long start, long end) throws Exception;
	
	/**
	 * 返回有序集中，成员的分数值
	 * @param key
	 * @param value
	 * @throws Exception
	 * @return Double
	 */
	public Double zScore(String key, Object value) throws Exception;
	
	/**
	 *  移除有序集合中的一个或多个成员，不存在的成员将被忽略。
	 * @param key
	 * @param values
	 * @throws Exception
	 * @return Long
	 */
	public Long zRemove(String key, Object... values) throws Exception;
	
	/**
	 * 有序集合中对指定成员的分数加上增量 delta
	 * @param key
	 * @param value
	 * @throws Exception
	 * @return Double
	 */
	public Double zIncrby(String key, String value, double delta) throws Exception;
	
	/**
	 * 设置有效期时间 秒
	 * @param key
	 * @param seconds
	 * @throws Exception
	 * @return Boolean
	 */
	public Boolean setExpire(String key, long seconds) throws Exception;
	
}
