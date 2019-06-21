package com.venux.redis.dto.param;

import com.venux.redis.constant.ZredisConstant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
 * @Title: ZJS 
 * @ClassName: DistlockApiParam 
 * @Description: 接口参数
 * @author liuzy
 * @date 2018年9月19日 下午2:29:11  
 */
@ApiModel
public class CacheApiParam<T,E> extends CommonApiParam<T> {
	
	@ApiModelProperty("value")
	private E value;
	@ApiModelProperty("有效时间expireSeconds，默认："+ZredisConstant.CACHE_EXPIRESECONDS +" ; 永久：-1")
	private long expireSeconds;
	
	/**
	 * @return the value
	 */
	public E getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(E value) {
		this.value = value;
	}

	/**
	 * @return the expireSeconds
	 */
	public long getExpireSeconds() {
		return expireSeconds;
	}
	
	/**
	 * @param expireSeconds the expireSeconds to set
	 */
	public void setExpireSeconds(long expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

}
