package com.venux.redis.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venux.redis.annotation.CheckParam;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dao.RedisServiceDao;
import com.venux.redis.dto.param.AuthApiParam;
import com.venux.redis.service.AuthorityService;
import com.venux.redis.utils.ZredisUtils;

/** 
 * @ClassName: StringRedisService 
 * @Description: Redis封装操作工具类
 * @author liuzy
 * @date 2018年9月13日 下午4:15:25  
 */
@Service
public class AuthorityServiceImpl implements AuthorityService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisServiceDao redisServiceDao;

	@Override
	@CheckParam(type=AuthApiParam.class)
	public void addAuth(AuthApiParam<String, String> authApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(authApiParam.getKey(), ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_STRING);
		redisServiceDao.set(builderKey, authApiParam.getValue());
	}

	@Override
	@CheckParam
	public String getAuth(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_STRING);
		return redisServiceDao.get(builderKey);
	}

	@Override
	@CheckParam
	public Boolean delAuth(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_STRING);
		return redisServiceDao.remove(builderKey);
	}

	@Override
	@CheckParam
	public long addAuthToSet(AuthApiParam<String, String[]> authApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(authApiParam.getKey(), ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_SET);
		return redisServiceDao.sAdd(builderKey, authApiParam.getValue());
	}

	@Override
	@CheckParam
	public long delAuthPropertyFromSet(AuthApiParam<String, String> authApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(authApiParam.getKey(), ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_SET);
		return redisServiceDao.sRemove(builderKey, authApiParam.getValue());
	}

	@Override
	@CheckParam
	public long delAuthPropertysFromSet(AuthApiParam<String, String[]> authApiParam) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(authApiParam.getKey(), ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_SET);
		return redisServiceDao.sRemove(builderKey, authApiParam.getValue());
	}
	
	@Override
	@CheckParam
	public Boolean delAuthFromSet(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_SET);
		return redisServiceDao.remove(builderKey);
	}

	@Override
	@CheckParam
	public Set<String> getAuthFromSet(String key) throws Exception {
		String builderKey = ZredisUtils.keyBuilder(key, ZredisUtils.ZredisPrefix.AUTHORITY, ZredisConstant.ZREDIS_TYPE_SET);
		 Set<String> members = redisServiceDao.sGetMembers(builderKey);
		return members;
	}
	
}
