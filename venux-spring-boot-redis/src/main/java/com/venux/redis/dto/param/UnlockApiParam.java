package com.venux.redis.dto.param;

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
public class UnlockApiParam<T> extends CommonApiParam<T>{
	
	@ApiModelProperty("客户端请求标识requestId")
	private String requestId;
	
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
	
}
