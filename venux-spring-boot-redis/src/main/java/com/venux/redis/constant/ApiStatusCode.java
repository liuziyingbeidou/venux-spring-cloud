package com.venux.redis.constant;


/** 
 * @ClassName: ApiStatusCode 
 * @Description: 接口调用错误做统一处理，返回码
 * @author liuzy
 * @date 2018年9月21日 下午5:13:59  
 */
public interface ApiStatusCode {

	/**
	 * 接口调用成功
	 */
	public static String RESPONSE_SUCCESS = "1000";
	
	/**
	 * 接口处理失败
	 */
	public static String RESPONSE_ERROR = "2000";
	
	/**
	 * 接口调用成功，处理失败
	 */
	public static String RESPONSE_BUSINESS_ERROR = "4004";
}
