package com.venux.redis.service.impl.strategy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dto.ConsumerDto;
import com.venux.redis.service.CommonService;
import com.venux.redis.service.strategy.CommunicationMode;

/** 
 * @ClassName: BroadcastMode 
 * @Description: 广播
 * @author liuzy
 * @date 2019年3月12日 上午9:14:00  
 */
@Service
public class BroadcastMode implements CommunicationMode {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;

	@Override
	public Integer getCommunicationMode() {
		return ZredisConstant.CommunicationModeEnum.BROADCAST.getValue();
	}

	@Override
	public void doCommunication(ConsumerDto consumer) throws Exception {
		logger.info("广播开始: {} {}", consumer.getConsumerKey(), consumer.getServiceName());
		List<ServiceInstance> serviceInstances =  commonService.getServiceInstanceByAppName(consumer.getServiceName()); 
        for(ServiceInstance serviceInstance : serviceInstances){
        	logger.info(consumer.getConsumerKey()+" 广播 project: " + serviceInstance.getServiceId() + ", Url：" + serviceInstance.getUri().toString());
        	commonService.notifySubscribe(serviceInstance.getUri().toString(), consumer.getConsumerKey(), ZredisConstant.TEMPLATE_TYPE_LOCAL);
        } 

	}
	
}
