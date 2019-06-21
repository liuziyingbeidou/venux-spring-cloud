package com.venux.redis.dto.param;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: PubSubApiParam 
 * @Description: 缓存发布消息参数
 * @author liuzy
 * @date 2019年2月25日 下午5:10:53  
 */
public class PubSubApiParam<T,E> extends CacheApiParam<T,E>{

	@ApiModelProperty("producer")
	private String producer;

	/**
	 * @return the producer
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * @param producer the producer to set
	 */
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
}
