package com.newcore.code.generator.controller.api;

import com.newcore.code.generator.model.CodeGeneratorRequestVO;

/**
 * 视图层代码生成服务接口
 * @author Zhou Chaowei
 */
public interface ViewGeneratorService {

    /**
     * 代码生成方法
     * @param codeGeneratorRequestVO 代码生成器请求VO模型
     * @return 生成结果
     */
    String generate(CodeGeneratorRequestVO codeGeneratorRequestVO);
}
