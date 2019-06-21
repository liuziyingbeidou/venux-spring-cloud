package com.venux.redis.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.venux.redis.Responses;
import com.venux.redis.dto.DealResultDto;
import com.venux.redis.dto.param.BaseApiParam;
import com.venux.redis.service.BaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 基础数据服务-API
 */
@Controller
@RequestMapping(value = { "/zredis/base/" }, produces = { "application/json" })
@Api(value = "/baseApi", description = "基础数据服务-接口说明")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseApi {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private BaseService baseService;

	@RequestMapping(value = { "/add" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "字符串键值对存储接口", notes = "字符串键值对存储接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> add(
			@ApiParam(value = "字符串键值对", required = true) @RequestBody BaseApiParam<String, String> baseApiParam) {
		try {
			baseService.add(baseApiParam);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_存放String类型数据异常，{}" , baseApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/get/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key查询键值字符串接口", notes = "根据key查询键值字符串接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> get(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			String result = baseService.get(key);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_取String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/del/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key删除键-值字符串接口", notes = "根据key删除键-值字符串接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> del(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			baseService.del(key);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_根据key删除对应的值异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/incrBy" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "将key中存储的数值自增delta接口", notes = "将key中存储的数值自增delta接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> incrBy(
			@ApiParam(value = "参数key", required = true) @RequestParam String key, @RequestParam(required=false, defaultValue="1") long  delta, @RequestParam(required=false, defaultValue="0") long expireSeconds) {
		try {
			Double result = baseService.incrBy(key, delta, expireSeconds);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_将key中存储的数值自增delta时异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/decrBy" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "将key中存储的数值自减delta接口", notes = "将key中存储的数值自减delta接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> decrBy(
			@ApiParam(value = "参数key", required = true) @RequestParam String key, @RequestParam(required=false, defaultValue="1") long  delta, @RequestParam(required=false, defaultValue="0") long expireSeconds) {
		try {
			Double result = baseService.decrBy(key, delta, expireSeconds);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_将key中存储的数值自减delta时异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
}
