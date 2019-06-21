package com.venux.redis.api;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.venux.redis.Responses;
import com.venux.redis.dto.DealResultDto;
import com.venux.redis.service.CacheService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 缓存服务-API
 */
@Controller
@RequestMapping(value = { "/zredis/cache/" }, produces = { "application/json" })
@Api(value = "/cacheApi", description = "缓存服务-接口说明")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CacheApi {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CacheService cacheService;
	
	@RequestMapping(value = { "/add" }, method = { RequestMethod.POST },produces = { "application/json" })
	@ApiOperation(value = "字符串键值对存储接口", notes = "字符串键值对存储接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand(commandKey = "cache")
	public ResponseEntity<DealResultDto> add(
			@ApiParam(value = "字符串键值对", required = true) @RequestParam String key, @RequestBody String value, @RequestParam(required=false, defaultValue="0") long expireSeconds) {
		try {
			cacheService.add(key, value, expireSeconds);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_add存放String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/addIfAbsent" }, method = { RequestMethod.POST },produces = { "application/json" })
	@ApiOperation(value = "字符串键值对存储(存在时不设值)接口", notes = "字符串键值对存储接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand(commandKey = "cache")
	public ResponseEntity<DealResultDto> addIfAbsent(
			@ApiParam(value = "字符串键值对", required = true) @RequestParam String key, @RequestBody String value, @RequestParam(required=false, defaultValue="0") long expireSeconds) {
		try {
			Boolean  result = cacheService.addIfAbsent(key, value, expireSeconds);
			String msg = result ? "处理成功" : "key存在";
			return Responses.dealSuccessReturnData(result, msg);
		} catch (Exception e) {
			logger.info("{}_addIfAbsent存放String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/get" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key查询键值字符串接口", notes = "根据key查询键值字符串接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand(commandKey = "cache")
	public ResponseEntity<DealResultDto> get(
			@ApiParam(value = "参数key", required = true) @RequestParam String key) {
		try {
			String result = cacheService.get(key);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_get取String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/del" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key删除接口", notes = "根据key删除接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> del(
			@ApiParam(value = "参数key", required = true) @RequestParam String key) {
		try {
			Boolean result = cacheService.del(key);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_del删除String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = { "/zAdd" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "添加有序集合接口", notes = "向有序集合添加成员，或者更新已存在成员的分数", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> zAdd(
			@ApiParam(value = "有序集合", required = true) @RequestParam String key, @RequestParam String value, @RequestParam double score,@RequestParam(required=false, defaultValue="-1") long expireSeconds) {
		try {
			Boolean result = cacheService.zAdd(key, value, score, expireSeconds);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_zAdd添加有序集合异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/zRangeWithScores" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "返回有序集合成指定区间内的成员接口", notes = "根据索引区间返回有序集合成指定区间内的成员接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> zRangeWithScores(
			@ApiParam(value = "参数key", required = true) @RequestParam String key, @RequestParam long start, @RequestParam long end) {
		try {
			Set<TypedTuple<String>> result = cacheService.zRangeWithScores(key, start, end);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_zRangeWithScores获取有序集合成指定区间内的成员异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/zRemove" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "移除有序集合中的一个或多个成员接口", notes = "移除有序集合中的一个或多个成员，不存在的成员将被忽略接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> zRemove(
			@ApiParam(value = "参数key", required = true) @RequestParam String key, @RequestParam Object[] values) {
		try {
			Long result = cacheService.zRemove(key, values);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_zRemove移除有序集合中的成员异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/zIncrby" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "有序集合中对指定成员的分数加delta接口", notes = "有序集合中对指定成员的分数加delta接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> zIncrby(
			@ApiParam(value = "参数key", required = true) @RequestParam String key, @RequestParam String value, @RequestParam(required=false, defaultValue="0") double  delta) {
		try {
			Double result = cacheService.zIncrby(key, value, delta);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_zIncrby有序集合中对指定成员的分数加delta异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/setExpire" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "设置有序集合有效期时间（秒）接口", notes = "设置有序集合有效期时间（秒）接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> setExpire(
			@ApiParam(value = "参数key", required = true) @RequestParam String key,  @RequestParam long  expireSeconds) {
		try {
			Boolean result = cacheService.setExpire(key, expireSeconds);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_setExpire设置有效期时间（秒）异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/publish" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "缓存发布接口", notes = "缓存发布接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand(commandKey = "cache")
	public ResponseEntity<DealResultDto> publish(
			@ApiParam(value = "缓存发布参数", required = true) @RequestParam String key, @RequestBody String value, @RequestParam(required=false, defaultValue="producer") String producer, @RequestParam(required=false, defaultValue="0") long expireSeconds) {
		try {
			Boolean result = cacheService.addPublish(key, value, expireSeconds, producer);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_publish消息发布异常，{}" , key, e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
}
