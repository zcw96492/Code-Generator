package com.newcore.code.generator.controller.api;

import com.newcore.code.generator.model.EntityClassInformation;

/**
 * FreeMarker转换服务接口
 * @author Zhou Chaowei
 */
public interface FreeMarkerParserService {

    /**
     * FreeMarker转换方法
     * @param entityClassInformation 实体信息模型
     * @param author 作者
     * @param packageRoute 包路径
     * @param returnTypeName 返回封装名称
     * @return
     */
    String freemarkerParser(EntityClassInformation entityClassInformation, String author, String packageRoute, String returnTypeName);
}
