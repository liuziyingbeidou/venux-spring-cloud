package com.venux.redis.dto.param;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @Title: ZJS 
 * @ClassName: CommonApiParam 
 * @Description: 用于转换
 * @author liuzy
 * @date 2019年1月14日 上午11:20:59  
 */
public class CommonApiParam<T> {
	
	@ApiModelProperty("key")
	private T key;

	/**
	 * @return the key
	 */
	public T getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(T key) {
		this.key = key;
	}
	
}
