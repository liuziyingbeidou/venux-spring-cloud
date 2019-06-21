package com.venux.redis.service.impl.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dto.ConsumerDto;
import com.venux.redis.service.CommonService;
import com.venux.redis.service.strategy.CommunicationMode;

/** 
 * @ClassName: UnicastMode 
 * @Description: 单播
 * @author liuzy
 * @date 2019年3月12日 上午9:13:18  
 */
@Service
public class UnicastMode implements CommunicationMode {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CommonService commonService;
	
	private String protocol = "http://";
	
	@Override
	public Integer getCommunicationMode() {
		return ZredisConstant.CommunicationModeEnum.UNICAST.getValue();
	}

	@Override
	public void doCommunication(ConsumerDto consumer) throws Exception {
		String uri = protocol;
		if(ZredisConstant.TEMPLATE_TYPE_REMOTE == consumer.getServiceType()){
			uri = uri + consumer.getServiceName();
			logger.info("单播开始: {} {}", consumer.getConsumerKey(), uri);
			commonService.notifySubscribe(uri, consumer.getConsumerKey(), ZredisConstant.TEMPLATE_TYPE_REMOTE);
		}else{
			uri = uri + consumer.getServiceName() + ":" + consumer.getServicePort() ;
			logger.info("单播开始: {} {}", consumer.getConsumerKey(), uri);
			commonService.notifySubscribe(uri, consumer.getConsumerKey(), ZredisConstant.TEMPLATE_TYPE_LOCAL);
		}

	}

}
