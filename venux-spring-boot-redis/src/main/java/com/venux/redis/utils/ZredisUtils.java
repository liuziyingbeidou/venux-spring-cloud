package com.venux.redis.utils;

import com.venux.redis.constant.ZredisConstant;

/** 
 * @ClassName: ZredisUtils 
 * @Description: redis操作工具类
 * @author liuzy
 * @date 2018年10月12日 上午9:13:35  
 */
public class ZredisUtils {

	public static enum ZredisPrefix{
		/**
		 * 基础服务，权限服务，分布式锁，缓存，订阅
		 */
		BASE,AUTHORITY,DISTLOCK,CACHE,PUBSUB
	}
	
	/**
	 * 构建key
	 * @param key 原始key
	 * @param prefix 标识服务前缀
	 * @param type 存储类型
	 * @return String
	 */
	public static String keyBuilder(String key,ZredisPrefix prefix,String type){
		
		StringBuffer newKey = new StringBuffer();
		newKey.append(prefix).append(ZredisConstant.KEY_SEPARATOR)
		.append(type.trim()).append(ZredisConstant.KEY_SEPARATOR)
		.append(key.trim());
		return newKey.toString();
	}
	
}
