package com.newcore.code.generator.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义字符串工具类
 * @author zhouchaowei
 */
public class StringCustomUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringCustomUtils.class);

    /**
     * 将字符串转换为首字母大写的字符串
     * @param str 待转换的字符串
     * @return 首字母大写的字符串
     */
    public static String toUpperCaseForFirstWord(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    /**
     * 将字符串转换为首字母小写的字符串
     * @param str 待转换的字符串
     * @return 首字母小写的字符串
     */
    public static String toLowerCaseForFirstWord(String str){
        return StringUtils.isNotBlank(str) ? str.substring(0,1).toLowerCase() + str.substring(1) : "";
    }

    /**
     * 将带下划线的字符串转换为驼峰式命名字段
     * @param column 字段名称
     * @return 首字母小写的字符串
     */
    public static String underLineToCamelCase(String column) {
        StringBuilder stringBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(column)){
            boolean flag = false;
            for (int i = 0 ; i < column.length() ; i++) {
                char ch = column.charAt(i);
                if("_".charAt(0) == ch){
                    flag = true;
                }else{
                    if(flag){
                        stringBuilder.append(Character.toUpperCase(ch));
                        flag = false;
                    }else{
                        stringBuilder.append(ch);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
