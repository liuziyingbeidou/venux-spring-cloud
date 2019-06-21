package com.venux.redis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 业务处理结果DTO
 */
@ApiModel
public class DealResultDto<E> {

    @ApiModelProperty("结果码,参考com.zjs.redis.constant.ApiStatusCode")
    private String status;
    @ApiModelProperty("根据实际返回数组或JSON对象")
    private E data;
    @ApiModelProperty("失败原因")
    private String msg;
	
	/**
	 * @return the code
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param code the code to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the data
	 */
	public E getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "DealResultDto [status=" + status + ", data=" + data + ", msg=" + msg + "]";
	}
	
}
