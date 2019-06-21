package com.venux.redis.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.venux.redis.annotation.ZredisCache;
import com.venux.redis.dto.ConsumerDto;
import com.venux.redis.service.ConsumerService;

/** 
 * @ClassName: ConsumerServiceImpl 
 * @Description: 订阅服务
 * @author liuzy
 * @date 2019年3月11日 上午11:45:19  
 */
@SuppressWarnings("unchecked")
@Service
public class ConsumerServiceImpl implements ConsumerService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 消费key前缀
	 */
	private final String redisKeyPre = "sub:db";
	
	/**
	 * 获取消费列表SQL
	 */
	private static String comsumerSql = "SELECT M.CONSUMER_KEY,B.SERVICE_TYPE, B.SERVICE_NAME, B.SERVICE_PORT,B.COMMUNICATION_MODE FROM HDS_REDIS_SUBSCRIBE_B B LEFT JOIN HDS_REDIS_SUBSCRIBE M ON M.ID=B.P_ID WHERE B.YN=1 AND M.YN=1 AND M.CONSUMER_KEY=?";

	@Override
	@ZredisCache(group=redisKeyPre, key="#key",type=ConsumerDto.class)
	public List<ConsumerDto> getComsumer(String key) throws Exception {
		Object[] args = new Object[]{key};
		RowMapper<ConsumerDto> rm = BeanPropertyRowMapper.newInstance(ConsumerDto.class);
		List<ConsumerDto> consumers = jdbcTemplate.query(comsumerSql,args,rm);
		logger.info("{} 订阅redis消费者列表大小：{}", key, consumers.size());
		return consumers;
	}
	
}
