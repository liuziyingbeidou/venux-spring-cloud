package com.venux.redis.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.venux.redis.Responses;
import com.venux.redis.constant.ZredisConstant;
import com.venux.redis.dto.DealResultDto;
import com.venux.redis.dto.param.AddLockApiParam;
import com.venux.redis.dto.param.UnlockApiParam;
import com.venux.redis.exception.BusinessException;
import com.venux.redis.service.DistlockService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 分布式锁服务-API
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping(value = { "/zredis/distlock/" }, produces = { "application/json" })
@Api(value = "/DistlockApi", description = "分布式锁服务-接口说明")
public class DistlockApi {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private DistlockService distlockService;
	
	@RequestMapping(value = { "/addLock" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "Redis加锁接口", notes = "Redis加锁接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> addLock(
			@ApiParam(value = "加锁参数", required = true) @RequestBody AddLockApiParam<String> addLockApiParam) {
		try {
			distlockService.addLock(addLockApiParam);
			return Responses.dealSuccess();
		}catch(BusinessException e){
			logger.info("{} 加锁失败, {}" , addLockApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: "+e.getMessage());
		}catch (Exception e) {
			logger.error("{} 加锁失败, 网络异常, {}" , addLockApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: 网络异常");
		}
	}
	
	@RequestMapping(value = { "/addLockRetry" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "Redis加锁重试接口", notes = "如果获取锁失败，进行重试"+ ZredisConstant.DISTLOCK_MAX_RETRY_TIMES +"次，每次重试之前sleep等待的"+ZredisConstant.DISTLOCK_RETRY_INTERVAL_TIME_MILLIS+"毫秒数", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand(commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value =ZredisConstant.DISTLOCK_RETRY_API_HYSTRIX_TIMEOUT))
	public ResponseEntity<DealResultDto> addLockRetry(
			@ApiParam(value = "加锁参数", required = true) @RequestBody AddLockApiParam<String> addLockApiParam) {
		try {
			distlockService.addLockRetry(addLockApiParam);
			return Responses.dealSuccess();
		}catch(BusinessException e){
			logger.info("{} 加锁失败, {}" , addLockApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: "+e.getMessage());
		}catch (Exception e) {
			logger.error("{} 加锁失败, 网络异常, {}" , addLockApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: 网络异常");
		}
	}
	
	@RequestMapping(value = { "/unlock" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "Redis解锁接口", notes = "Redis解锁接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> unlock(
			@ApiParam(value = "解锁参数", required = true) @RequestBody UnlockApiParam<String> unlockApiParam) {
		try {
			distlockService.unlock(unlockApiParam);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{} 解锁异常, {}" , unlockApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/addLocks" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "Redis加锁(多key)接口", notes = "Redis加锁(多key)接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> addLocks(
			@ApiParam(value = "加锁参数", required = true) @RequestBody AddLockApiParam<String[]> addLockApiParams) {
		try {
			distlockService.addLocks(addLockApiParams);
			return Responses.dealSuccess();
		}catch(BusinessException e){
			logger.info("{} 加锁失败(多key), {}" , addLockApiParams.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: "+e.getMessage());
		}catch (Exception e) {
			logger.error("{} 加锁失败(多key), 网络异常, {}" , addLockApiParams.getKey(),e.getMessage(),e);
			return Responses.dealError("加锁失败: 网络异常");
		}
	}
	
	@RequestMapping(value = { "/unlocks" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "Redis解锁(多key)接口", notes = "Redis解锁(多key)接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> unlocks(
			@ApiParam(value = "解锁参数", required = true) @RequestBody UnlockApiParam<String[]> unlockApiParams) {
		try {
			distlockService.unlocks(unlockApiParams);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{} 解锁异常(多key), {}" , unlockApiParams.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
}
