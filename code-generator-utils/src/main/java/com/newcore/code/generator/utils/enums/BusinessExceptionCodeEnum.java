package com.newcore.code.generator.utils.enums;

import com.newcore.code.generator.utils.exception.BusinessException;

import java.util.Arrays;
import java.util.Optional;

/**
 * 业务异常错误码统一处理枚举类
 * @author zhouchaowei
 */
public enum BusinessExceptionCodeEnum {

    SUCCESS("00000","success"),
    FAIL("99999","fail"),

    ERROR_0001("ERROR_0001","未传入有效的SQL语句"),
    ERROR_0002("ERROR_0002","未传入有效的包路径"),
    ERROR_0003("ERROR_0003","未传入有效的返回类型"),
    ERROR_0004("ERROR_0004","SQL语句结构有误,请核实后再试!");

    /** 异常状态码 */
    private final String code;

    /** 异常提示信息 */
    private final String message;

    /**
     * 异常处理构造方法
     * @param code 异常状态码
     * @param message 异常提示信息
     */
    BusinessExceptionCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    /**
     * 查找对应的Key值
     * @param key
     * @return com.joiest.market.common.enumclass.BusinessExceptionCodeEnum
     */
    public static BusinessExceptionCodeEnum valueOfKey(String key) {
        Optional<BusinessExceptionCodeEnum> optionalValue = Arrays.stream(values()).filter((item) -> {
            return item.getCode().equals(key);
        }).findFirst();
        if (optionalValue.isPresent()) {
            return optionalValue.get();
        } else {
            throw new BusinessException("Can't find enum BusinessExceptionCodeEnum for key " + key);
        }
    }
}
