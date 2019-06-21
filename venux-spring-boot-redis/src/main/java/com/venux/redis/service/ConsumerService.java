package com.venux.redis.service;

import java.util.List;

import com.venux.redis.dto.ConsumerDto;

/** 
 * @ClassName: ConsumerService 
 * @Description: 订阅服务
 * @author liuzy
 * @date 2019年3月11日 上午11:34:21  
 */
public interface ConsumerService {

	/**
	 * 根据key获取订阅者
	 * @param key
	 * @throws Exception
	 * @return List<ConsumerDto>
	 */
	List<ConsumerDto> getComsumer(String key) throws Exception;
}
