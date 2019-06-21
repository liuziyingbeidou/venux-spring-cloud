package com.venux.redis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.service.PubSubService;

/** 
 * @ClassName: PubSubServiceImpl 
 * @Description:  发布订阅
 * @author liuzy
 * @date 2019年2月25日 下午3:06:03  
 */
@Service
public class PubSubServiceImpl implements PubSubService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisServiceDao redisServiceDao;

	@Override
	public boolean publish(String channel, Object message) throws Exception {
		logger.info("PUB {} 发布消息: {}", channel, message);
		redisServiceDao.publish(channel, message);
		return true;
	}

}
