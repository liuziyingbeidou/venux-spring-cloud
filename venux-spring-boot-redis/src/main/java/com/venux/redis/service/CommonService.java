package com.venux.redis.service;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;

/** 
 * @Title: ZJS 
 * @ClassName: CommonService 
 * @Description: 公共服务
 * @author liuzy
 * @date 2019年2月25日 下午8:06:08  
 */
public interface CommonService {

	/**
	 * 通知订阅
	 * @param uri
	 * @param key
	 * @throws Exception
	 * @return void
	 */
	void notifySubscribe(String uri, String key, int templateType) throws Exception;
	
	/**
	 * 根据服务名获取Eureka中的实例
	 * @param appName
	 * @throws Exception
	 * @return List<ServiceInstance>
	 */
	 List<ServiceInstance> getServiceInstanceByAppName(String appName) throws Exception;
}
