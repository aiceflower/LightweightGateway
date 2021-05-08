package com.scnu.lwg.config;

import com.scnu.lwg.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kin
 * @description common exception handler
 * @email kinsanities@sina.com
 * @time 2021/5/3 1:00 下午
 */

@ControllerAdvice
@RestController
@Slf4j
public class CommonExceptionHandler {
	/**
	 * Java一般异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result exceptionHandler(Exception e){
		e.printStackTrace();
		log.error(e.getMessage());
		return Result.fail();
	}

	/**
	 * 拦截自定义异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public Result exceptionHandler(BaseException ex){
		ex.printStackTrace();
		log.error(ex.getMsg());
		return Result.fail(ex.getCode(), ex.getMsg());
	}
}
