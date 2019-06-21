package com.venux.redis.api;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.venux.redis.Responses;
import com.venux.redis.dto.DealResultDto;
import com.venux.redis.dto.param.AuthApiParam;
import com.venux.redis.service.AuthorityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 权限服务API
 */
@Controller
@RequestMapping(value = { "/zredis/authority/" }, produces = { "application/json" })
@Api(value = "/authorityApi", description = "权限服务-接口说明")
public class AuthorityApi {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private AuthorityService authorityService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/addAuth" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "字符串键值对存储接口", notes = "字符串键值对存储接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> addAuth(
			@ApiParam(value = "字符串键值对", required = true) @RequestBody AuthApiParam<String, String> authApiParam) {
		try {
			authorityService.addAuth(authApiParam);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_存放String类型数据异常，{}" , authApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/getAuth/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key查询键值字符串接口", notes = "根据key查询键值字符串接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> getAuth(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			String result = authorityService.getAuth(key);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_取String类型数据异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/delAuth/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key删除键-值字符串接口", notes = "根据key删除键-值字符串接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> delAuth(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			authorityService.delAuth(key);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_根据key删除对应的值异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/addAuthToSet" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "集合Set存储接口", notes = "集合Set存储接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> addAuthToSet(
			@ApiParam(value = "支持单值或多值", required = true) @RequestBody AuthApiParam<String, String[]> authApiParam) {
		try {
			authorityService.addAuthToSet(authApiParam);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_向名称为key的set中添加元素value异常，{}" , authApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/delAuthPropertysFromSet" }, method = { RequestMethod.POST }, produces = { "application/json" })
	@ApiOperation(value = "根据key-values删除集合Set接口", notes = "根据key-values删除集合Set接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> delAuthPropertyFromSet(
			@ApiParam(value = "参数key-value", required = true) @RequestBody AuthApiParam<String, String[]> authApiParam) {
		try {
			authorityService.delAuthPropertysFromSet(authApiParam);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_根据key-values删除集合Set接口异常，{}" , authApiParam.getKey(),e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/delAuthFromSet/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key删除集合Set接口", notes = "根据key删除集合Set接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> delAuthFromSet(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			authorityService.delAuthFromSet(key);
			return Responses.dealSuccess();
		} catch (Exception e) {
			logger.info("{}_根据key删除集合Set接口异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/getAuthFromSet/{key}" }, method = { RequestMethod.GET }, produces = { "application/json" })
	@ApiOperation(value = "根据key查询集合Set接口", notes = "根据key查询集合Set接口说明", response = String.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "请求处理失败"), @ApiResponse(code = 404, message = "请求处理失败") })
	@HystrixCommand
	public ResponseEntity<DealResultDto> getAuthFromSet(
			@ApiParam(value = "参数key", required = true) @PathVariable String key) {
		try {
			Set<String> result = authorityService.getAuthFromSet(key);
			return Responses.dealSuccessReturnData(result);
		} catch (Exception e) {
			logger.info("{}_返回名称为key的set的所有元素异常，{}" , key,e.getMessage(),e);
			return Responses.dealError(e.getMessage());
		}
	}
}
