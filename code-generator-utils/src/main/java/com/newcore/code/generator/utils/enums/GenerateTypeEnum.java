package com.newcore.code.generator.utils.enums;

import com.newcore.code.generator.utils.exception.BusinessException;

import java.util.Arrays;
import java.util.Optional;

/**
 * 代码生成种类枚举类
 * @author zhouchaowei
 */
public enum GenerateTypeEnum {

    MODEL("MODEL","模型层代码生成"),
    DAO("DAO","持久层代码生成"),
    CONTROLLER("CONTROLLER","控制器层代码生成");

    /** 代码种类 */
    private final String code;

    /** 释义信息 */
    private final String message;

    /**
     * 异常处理构造方法
     * @param code 异常状态码
     * @param message 异常提示信息
     */
    GenerateTypeEnum(String code, String message) {
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
     * @return GenerateTypeEnum
     */
    public static GenerateTypeEnum valueOfKey(String key) {
        Optional<GenerateTypeEnum> optionalValue = Arrays.stream(values()).filter((item) -> {
            return item.getCode().equals(key);
        }).findFirst();
        if (optionalValue.isPresent()) {
            return optionalValue.get();
        } else {
            throw new BusinessException("Can't find enum GenerateTypeEnum for key " + key);
        }
    }
}
