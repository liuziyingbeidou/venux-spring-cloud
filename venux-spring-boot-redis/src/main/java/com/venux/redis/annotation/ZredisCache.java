package com.venux.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.venux.redis.constant.ZredisConstant;

/** 
 * @ClassName: ZredisCache 
 * @Description: redis缓存
 * @author liuzy
 * @date 2019年4月1日 下午1:42:12  
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZredisCache {

	/**
	 * 分组 : 避免key冲突，一般是模块/组名/表名
	 */
	String group();
	/**
	 * SpEL
	 */
	String key() default "";
	
	/**
	 * 类型: 用户返回List<T>时指定T
	 */
	Class type();
	
	/**
	 * 是否开启缓存
	 */
    boolean enable() default true;
	
	 /**
	  * 超时时间 
	  */
    long expireSeconds() default ZredisConstant.CACHE_EXPIRESECONDS;
}
