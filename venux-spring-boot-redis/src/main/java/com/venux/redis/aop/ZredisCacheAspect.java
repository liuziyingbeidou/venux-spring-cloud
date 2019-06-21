package com.venux.redis.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.venux.redis.annotation.ZredisCache;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.service.CacheService;
import com.venux.redis.utils.PubUtils;

/**
 * @Title: ZJS
 * @ClassName: ZredisCacheAspect
 * @Description: zrediscache注解实现
 * @author liuzy
 * @date 2019年4月1日 下午2:07:44
 */
@Component
@Aspect
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ZredisCacheAspect {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheService cacheService;

	@Around("@annotation(com.zjs.redis.annotation.ZredisCache)")
	public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;

		Method method = getMethod(proceedingJoinPoint);
		// 获取注解对象
		ZredisCache zredisCache = method.getAnnotation(ZredisCache.class);
		boolean enable = zredisCache.enable();
		if (!enable) {
			return proceedingJoinPoint.proceed();
		}
		String cacheKey = null;
		try {
			String acKey = parseKey(zredisCache.key(), method, proceedingJoinPoint.getArgs());
			cacheKey = zredisCache.group() + ZredisConstant.KEY_SEPARATOR + acKey;
			String cacheResult = cacheService.get(cacheKey);
			if (PubUtils.isNull(cacheResult)) {
				result = proceedingJoinPoint.proceed();
				cacheService.add(cacheKey, this.serialize(result), zredisCache.expireSeconds());
				logger.info("key={} 添加到缓存", cacheKey);
				return result;
			}
			Class returnType = ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType();
			result = deserialize(cacheResult, returnType, zredisCache.type());
			logger.info("key={} 从缓存中获取", cacheKey);
		} catch (Exception e) {
			logger.error("key={} 读取缓存服务异常", cacheKey);
			result = proceedingJoinPoint.proceed();
		}
		return result;
	}

	/**
	 * @Title: getMethod
	 * @Description: 获取被拦截方法对象
	 * @param joinPoint
	 * @return
	 */
	protected Method getMethod(JoinPoint joinPoint) throws Exception {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		return method;
	}

	/**
	 * 获取缓存的key key 定义在注解上，支持SPEL表达式
	 */
	private String parseKey(String key, Method method, Object[] args) {
		
		//创建解析器
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression(key);
		
		//设置解析上下文 jdk1.8+
		EvaluationContext context = new StandardEvaluationContext();
		DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
		
		String[] parameterNames = discoverer.getParameterNames(method);
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		
		return expression.getValue(context, String.class);
	}

	/**
	 * 序列化
	 */
	protected String serialize(Object target) {
		return JSON.toJSONString(target);
	}

	/**
	 * 反序列化
	 */
	protected Object deserialize(String jsonString, Class clazz, Class modelType) {
		// 序列化结果应是List对象
		if (clazz.isAssignableFrom(List.class)) {
			return JSON.parseArray(jsonString, modelType);
		}
		// 序列化结果是普通对象
		return JSON.parseObject(jsonString, clazz);
	}

}
