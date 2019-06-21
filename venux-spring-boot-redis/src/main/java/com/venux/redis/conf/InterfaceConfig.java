package com.venux.redis.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


/**
 * 外部接口地址配置类
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = InterfaceConfig.PREFIX)
public class InterfaceConfig {

    public static final String PREFIX = "interface";
    
    public String notifySubscribeApi;

	/**
	 * @return the notifySubscribeApi
	 */
	public String getNotifySubscribeApi() {
		return notifySubscribeApi;
	}

	/**
	 * @param notifySubscribeApi the notifySubscribeApi to set
	 */
	public void setNotifySubscribeApi(String notifySubscribeApi) {
		this.notifySubscribeApi = notifySubscribeApi;
	}
   
}

