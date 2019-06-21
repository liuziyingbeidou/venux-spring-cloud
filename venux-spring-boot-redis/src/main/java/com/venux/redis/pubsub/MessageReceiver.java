package com.venux.redis.pubsub;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dao.RedisLock;
import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.dto.param.PubSubApiParam;
import com.venux.redis.service.Receiver;
import com.venux.redis.service.strategy.CommunicationMode;
import com.venux.redis.utils.ZredisUtils;

@Component
public class MessageReceiver {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Receiver receiver;
	
	@Autowired
	private RedisServiceDao redisServiceDao;
 
	private Map<Integer,CommunicationMode> mapMode = Maps.newHashMap();
	
	/**
	 * 构造注入
	 */
	public MessageReceiver(List<CommunicationMode> communicationModes){
		for (CommunicationMode mode : communicationModes) {
			mapMode.put(mode.getCommunicationMode(), mode);
		}
	}
	
    /**
     * 接收消息 此处使用分布式锁实现消费负载均衡
     * @param message
     * @throws Exception
     * @return void
     */
	@SuppressWarnings("unchecked")
    public void receiveMessage(String message) throws Exception{
		logger.info("SUB 接收到消息：{}", message);
		if(message == null){
			return ;
		}
		PubSubApiParam<String, String> pubSubApiParam = JSON.parseObject(message, PubSubApiParam.class);
		String builderKey = ZredisUtils.keyBuilder(pubSubApiParam.getKey(), ZredisUtils.ZredisPrefix.DISTLOCK, ZredisConstant.ZREDIS_TYPE_PUBSUB);
    	try(RedisLock lock = redisServiceDao.getLock(builderKey, UUID.randomUUID().toString(), ZredisConstant.DISTLOCK_EXPIRESECONDS)) {
    	      if (lock != null) {
    	    	  logger.info("{} 获取锁成功", builderKey);
    	    	  receiver.receiveMessage(pubSubApiParam);
    	      }else{
    	    	  logger.info("{} 获取锁失败", builderKey);
    	      }
    	  }catch (Exception e) {
    		  logger.info("{} 通知订阅者异常: {}", builderKey, e.getMessage());
    	  }
    }
	
}