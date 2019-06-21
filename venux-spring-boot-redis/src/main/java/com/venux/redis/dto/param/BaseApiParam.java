package com.venux.redis.dto.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: BaseApiParam 
 * @Description: 接口参数
 * @author liuzy
 * @date 2018年9月19日 下午2:29:11  
 */
@ApiModel
public class BaseApiParam<T,E>extends CommonApiParam<T> {
	
	@ApiModelProperty("value")
	private E value;
	
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
	
}
