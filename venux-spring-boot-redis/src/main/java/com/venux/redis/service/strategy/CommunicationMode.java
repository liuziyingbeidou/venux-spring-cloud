package com.venux.redis.service.strategy;

import com.venux.redis.dto.ConsumerDto;

/** 
 * @Title: ZJS 
 * @ClassName: CommunicationMode 
 * @Description: 通讯模式策略
 * @author liuzy
 * @date 2019年3月12日 上午9:07:55  
 */
public interface CommunicationMode {
	
	/**
	 * 通讯模式
	 * @return int
	 */
	public Integer getCommunicationMode();
	
	/**
	 * 通知
	 * @param consumer
	 * @throws Exception
	 * @return void
	 */
	public void doCommunication(ConsumerDto consumer) throws Exception;

}
