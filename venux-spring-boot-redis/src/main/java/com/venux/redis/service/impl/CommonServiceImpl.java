package com.venux.redis.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.venux.redis.conf.InterfaceConfig;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.exception.BusinessException;
import com.venux.redis.service.CommonService;
import com.venux.redis.utils.PubUtils;

/** 
 * @ClassName: CommonServiceImpl 
 * @Description: 公共服务实现
 * @author liuzy
 * @date 2019年2月25日 下午8:11:23  
 */
@Service
public class CommonServiceImpl implements CommonService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private InterfaceConfig interfaceConfig;
	
	@Autowired  
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private RestTemplate restTemplateLocal = new RestTemplate();
	
	@Override
	@Retryable(value= {Exception.class},maxAttempts = 2,backoff = @Backoff(delay = 2000l,multiplier = 1))
	public void notifySubscribe(String uri, String key, int templateType) throws Exception {
		String notifyResult = null;
		String url = uri+interfaceConfig.getNotifySubscribeApi();
		try {
			logger.info("通知订阅API：uri={}；rediskey={}", url, key);
			if(ZredisConstant.TEMPLATE_TYPE_REMOTE == templateType){
				notifyResult =  restTemplate.postForObject(url, null, String.class, key);
			}else{
				notifyResult =  restTemplateLocal.postForObject(url, null, String.class, key);
			}
            logger.info("订阅 key={} 通知 接口={} ,返回信息：{}",key, url, notifyResult);
            JSONObject notifyResultJson = JSON.parseObject(notifyResult);
            Integer code = notifyResultJson.getInteger("status");
            if(!ZredisConstant.API_CODE.equals(code)){
            	String description = notifyResultJson.getString("msg");
            	logger.error("通知订阅API：uri={}；rediskey={}; 异常({}): {}", url, key, code, description);
            }
		} catch (HttpClientErrorException e) {
			logger.error("通知订阅API：uri={}；rediskey={}; 异常: {}", url, key, e.getMessage());
    	    throw new BusinessException(e.getMessage());
    	}catch(Exception e){
    		logger.error("通知订阅API：uri={}；rediskey={}; 异常: {}", url, key, e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public List<ServiceInstance> getServiceInstanceByAppName(String appName) throws Exception {
		if(PubUtils.isNull(appName)){
			throw new BusinessException("服务名空");
		}
		List<ServiceInstance> serviceInstances  = discoveryClient.getInstances(appName.trim().toLowerCase());
		if(PubUtils.isNull(serviceInstances)){
			throw new BusinessException("在注册中心查找不到服务: "+appName.toLowerCase());
		}
		return serviceInstances;
	}

}
