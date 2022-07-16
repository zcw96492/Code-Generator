package com.newcore.code.generator.utils;

import com.newcore.code.generator.utils.enums.BusinessExceptionCodeEnum;

import java.util.ArrayList;
import java.util.Date;

/**
 * REST接口请求响应对象类
 * @author zhouchaowei
 */
public class RestServerResponse<T> {

	/** 响应码 */
	private String code;
	/** 响应信息 */
	private String message;
	/** 响应数据 */
	private T data;
	/** 响应来源服务名 */
	private String responseSourceServiceName;
	/** 响应时间戳 */
	private String responseTime;
	/** 响应唯一标识码 */
	private String transactionCode;

	/**
	 * 私有构造方法
	 *【用于构造响应的数据结构】
	 * @param code 响应码
	 * @param message 响应信息
	 * @param data 响应数据
	 * @param responseSourceServiceName 响应来源服务名
	 */
	private RestServerResponse(String code, String message, T data, String responseSourceServiceName){
		this.code = code;
		this.message = message;
		this.data = data;
		this.responseSourceServiceName = responseSourceServiceName;
		this.responseTime = DateUtils.getFormatDateString(new Date(),"yyyy-MM-dd HH:mm:ss.SSS");
		this.transactionCode = UuidGenerateUtils.generateRandomSerialNumber();
	}

	/**
	 * 只返回成功响应码
	 * @param <T>
	 * @param responseSourceServiceName 响应来源服务名
	 * @return
	 */
	public static <T> RestServerResponse<T> createBySuccess(String responseSourceServiceName){
		return new RestServerResponse<>(
				BusinessExceptionCodeEnum.SUCCESS.getCode(),
				BusinessExceptionCodeEnum.SUCCESS.getMessage(),
				(T) new ArrayList(),
				responseSourceServiceName
		);
	}

	/**
	 * 只返回成功响应信息
	 * @param msg 响应信息
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createBySuccessMessage(String msg, String responseSourceServiceName){
		return new RestServerResponse<>(BusinessExceptionCodeEnum.SUCCESS.getCode(),msg, (T) new ArrayList(),responseSourceServiceName);
	}

	/**
	 * 只返回成功响应数据
	 * @param data 响应数据
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createBySuccessData(T data, String responseSourceServiceName){
		return new RestServerResponse<>(
				BusinessExceptionCodeEnum.SUCCESS.getCode(),
				BusinessExceptionCodeEnum.SUCCESS.getMessage(),
				data,
				responseSourceServiceName
		);
	}

	/**
	 * 只返回成功响应信息和响应数据
	 * @param msg 响应信息
	 * @param data 响应数据
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createBySuccess(String msg, T data, String responseSourceServiceName){
		return new RestServerResponse<>(BusinessExceptionCodeEnum.SUCCESS.getCode(),msg,data,responseSourceServiceName);
	}

	/**
	 * 只返回错误响应码
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createByError(String responseSourceServiceName){
		return new RestServerResponse<>(
				BusinessExceptionCodeEnum.FAIL.getCode(),
				BusinessExceptionCodeEnum.FAIL.getMessage(),
				(T) new ArrayList(),
				responseSourceServiceName
		);
	}

	/**
	 * 只返回错误响应信息
	 * @param errorMsg 响应信息
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createByErrorMessage(String errorMsg, String responseSourceServiceName){
		return new RestServerResponse<>(BusinessExceptionCodeEnum.FAIL.getCode(),errorMsg,(T) new ArrayList(),responseSourceServiceName);
	}

	/**
	 * 返回错误码和错误信息
	 * @param errorCode 响应码
	 * @param errorMsg 响应信息
	 * @param responseSourceServiceName 响应来源服务名
	 * @param <T>
	 * @return
	 */
	public static <T> RestServerResponse<T> createByErrorCodeMessage(String errorCode, String errorMsg, String responseSourceServiceName){
		return new RestServerResponse<>(errorCode,errorMsg,(T) new ArrayList(),responseSourceServiceName);
	}
}
