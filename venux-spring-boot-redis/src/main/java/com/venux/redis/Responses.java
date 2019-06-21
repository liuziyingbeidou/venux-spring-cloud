package com.venux.redis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.venux.redis.constant.ApiStatusCode;
import com.venux.redis.dto.DealResultDto;
import com.venux.redis.exception.BusinessErrorModel;

/**
 * 接口响应
 */
public class Responses {
	
    public static ResponseEntity ok() {
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 接口处理成功
     * @return
     */
    public static ResponseEntity dealSuccess() {
        DealResultDto dealResultDto = new DealResultDto();
        dealResultDto.setStatus(ApiStatusCode.RESPONSE_SUCCESS);
        return new ResponseEntity(dealResultDto,HttpStatus.OK);
    }
    
    /**
     * 接口处理成功，返回主数据
     * @return
     */
    public static <T> ResponseEntity dealSuccessReturnData(T data) {
        DealResultDto dealResultDto = new DealResultDto();
        dealResultDto.setStatus(ApiStatusCode.RESPONSE_SUCCESS);
        dealResultDto.setData(data);
        return new ResponseEntity(dealResultDto,HttpStatus.OK);
    }
    
    public static <T> ResponseEntity dealSuccessReturnData(T data, String msg) {
    	DealResultDto dealResultDto = new DealResultDto();
        dealResultDto.setStatus(ApiStatusCode.RESPONSE_SUCCESS);
        dealResultDto.setData(data);
        dealResultDto.setMsg(msg);
        return new ResponseEntity(dealResultDto,HttpStatus.OK);
    }
    
    public static ResponseEntity dealSuccess(String errorMessage) {
        DealResultDto dealResultDto = new DealResultDto();
        dealResultDto.setStatus(ApiStatusCode.RESPONSE_BUSINESS_ERROR);
        dealResultDto.setMsg(errorMessage);
        return new ResponseEntity(dealResultDto,HttpStatus.OK);
    }
    
    public static ResponseEntity dealError(String errorMessage) {
        DealResultDto dealResultDto = new DealResultDto();
        dealResultDto.setStatus(ApiStatusCode.RESPONSE_ERROR);
        dealResultDto.setMsg(errorMessage);
        return new ResponseEntity(dealResultDto,HttpStatus.OK);
    }

    public static ResponseEntity exception(HttpStatus httpStatus,BusinessErrorModel model) {
        return new ResponseEntity(model,httpStatus);
    }

    public static <T> ResponseEntity<T> ok(T model) {
        return new ResponseEntity(model, HttpStatus.OK);
    }
}
