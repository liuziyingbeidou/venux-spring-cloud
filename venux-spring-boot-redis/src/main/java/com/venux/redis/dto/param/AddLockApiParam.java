package com.venux.redis.dto.param;

import com.venux.redis.constant.ZredisConstant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: DistlockApiParam 
 * @Description: 接口参数
 * @author liuzy
 * @date 2018年9月19日 下午2:29:11  
 */
@ApiModel
public class AddLockApiParam<T> extends CommonApiParam<T>{
	
	@ApiModelProperty("客户端请求标识requestId")
	private String requestId;
	@ApiModelProperty("有效时间expireSeconds，默认："+ZredisConstant.DISTLOCK_EXPIRESECONDS)
	private long expireSeconds;

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
