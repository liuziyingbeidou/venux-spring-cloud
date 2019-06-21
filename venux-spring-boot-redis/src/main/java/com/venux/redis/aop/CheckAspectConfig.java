package com.venux.redis.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.venux.redis.dto.param.CommonApiParam;
import com.venux.redis.exception.BusinessException;
import com.venux.redis.utils.PubUtils;

/** 
 * @ClassName: CheckAspectConfig 
 * @Description: 校验参数切面
 * @author liuzy
 * @date 2018年9月20日 上午10:18:55  
 */
@Component
@Aspect
public class CheckAspectConfig {

    @Pointcut("@annotation(com.zjs.redis.annotation.CheckParam)")
    public void checkMethodParamAnnotationPointcut() {
    	
    }

    @Around(value = "checkMethodParamAnnotationPointcut()")
    public Object methodsAnnotatedAroundMethodParam(final ProceedingJoinPoint joinPoint) throws Throwable {
    	return joinPoint.proceed();
    }
    
    @Before(value = "checkMethodParamAnnotationPointcut()")
    public void methodsAnnotatedCheckMethodParam(final JoinPoint joinPoint) throws Throwable {
    	Object[] args = joinPoint.getArgs();
    	for (Object arg : args) {
    		if(CommonApiParam.class.isAssignableFrom(arg.getClass())){
    			CommonApiParam commonApiParam = (CommonApiParam) arg;
    			if(PubUtils.isNull(commonApiParam.getKey())){
    				throw new BusinessException("传入的key为空！");
    			}
    		}
    		if(String.class.isAssignableFrom(arg.getClass())){
    			if(PubUtils.isNull(arg)){
    				throw new BusinessException("传入的参数为空！");
    			}
    		}
		}
    }
}
