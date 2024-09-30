package com.base.config.exception;

import com.base.entity.response.APIResponse;
import com.base.entity.response.ErrorCodeEnum;
import com.base.tools.log.LogHelper;
import com.base.tools.string.StringBuilderUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;

/**
 * 控制器回执 - 错误处理
 */
@ControllerAdvice
public class DefaultExceptionHandler {

	/**
	 * 类字段验证错误处理
	 *
	 * @param e        错误信息
	 * @param response 响应信息
	 * @return 处理结果
	 */
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public APIResponse<String> handle(MethodArgumentNotValidException e, HttpServletResponse response) {
		//错误消息
		var error = new StringBuilderUtils();
		for (var item : e.getAllErrors()) {
			//字段名
			var fieldName = ((DefaultMessageSourceResolvable) Objects.requireNonNull(item.getArguments())[0]).getDefaultMessage();
			//提示
			var message = item.getDefaultMessage();
			error.append("[${fieldName}]$message；");
		}
		var message = "参数校验失败：" + error;
		//设置返回状态
		response.setStatus(ErrorCodeEnum.BadRequest.getValue());
		//请求返回
		return APIResponse.fail(ErrorCodeEnum.BadRequest, message);
	}

	/**
	 * 方法参数验证错误处理
	 *
	 * @param e        错误信息
	 * @param response 响应信息
	 * @return 处理结果
	 */
	@ResponseBody
	@ExceptionHandler(HandlerMethodValidationException.class)
	public APIResponse<String> handle(HandlerMethodValidationException e, HttpServletResponse response) {
		//错误消息
		var error = new StringBuilderUtils();
		for (var item : e.getAllErrors()) {
			//字段名
			var fieldName = ((DefaultMessageSourceResolvable) Objects.requireNonNull(item.getArguments())[0]).getDefaultMessage();
			//提示
			var message = item.getDefaultMessage();
			error.append("[${fieldName}]$message；");
		}
		var message = "参数校验失败：" + error;
		//设置返回状态
		response.setStatus(ErrorCodeEnum.BadRequest.getValue());
		//请求返回
		return APIResponse.fail(ErrorCodeEnum.BadRequest, message);
	}


	/**
	 * 控制器错误处理
	 *
	 * @param e        错误信息
	 * @param request  请求信息
	 * @param response 响应信息
	 * @return 处理结果
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public APIResponse<String> handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
		//错误日志
		StringBuilderUtils builder = new StringBuilderUtils();
		builder.appendFormatLine("【WebAPI】报错");
		builder.appendFormat("接口地址：%s", request.getRequestURI());
		LogHelper.error(e, builder.toString());

		//设置返回状态
		if (e instanceof NoResourceFoundException) {
			response.setStatus(ErrorCodeEnum.NotFound.getValue());
			//请求返回
			return APIResponse.fail(ErrorCodeEnum.NotFound);
		}
		else {
			response.setStatus(ErrorCodeEnum.ServerError.getValue());
			//请求返回
			return APIResponse.fail(ErrorCodeEnum.ServerError);
		}
	}
}