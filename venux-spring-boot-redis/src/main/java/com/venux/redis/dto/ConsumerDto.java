package com.venux.redis.dto;


/** 
 * @ClassName: ConsumerDto 
 * @Description: 订阅消费
 * @author liuzy
 * @date 2019年3月11日 上午11:37:19  
 */
public class ConsumerDto {

	/**
	 * 消费key
	 */
	private String consumerKey;
	/**
	 * 通讯模式 1-广播;2-单播
	 */
	private Integer communicationMode;
	/**
	 * 服务类型 1-微服务; 2-普通服务
	 */
	private Integer serviceType;
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * 服务端口号
	 */
	private Integer servicePort;
	
	
	/**
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * @return the communicationMode
	 */
	public Integer getCommunicationMode() {
		return communicationMode;
	}

	/**
	 * @param communicationMode the communicationMode to set
	 */
	public void setCommunicationMode(Integer communicationMode) {
		this.communicationMode = communicationMode;
	}

	/**
	 * @return the serviceType
	 */
	public Integer getServiceType() {
		return serviceType;
	}
	
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	/**
	 * @return the servicePort
	 */
	public Integer getServicePort() {
		return servicePort;
	}
	
	/**
	 * @param servicePort the servicePort to set
	 */
	public void setServicePort(Integer servicePort) {
		this.servicePort = servicePort;
	}
	
	
}
