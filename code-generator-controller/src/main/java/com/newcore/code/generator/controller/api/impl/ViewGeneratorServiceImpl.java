package com.newcore.code.generator.controller.api.impl;

import com.newcore.code.generator.controller.api.ViewGeneratorService;
import com.newcore.code.generator.model.CodeGeneratorRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 视图层代码生成服务接口实现类
 * @author Zhou Chaowei
 */
@Service("viewGeneratorService")
public class ViewGeneratorServiceImpl implements ViewGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(ViewGeneratorServiceImpl.class);

    /**
     * 代码生成方法
     * @param codeGeneratorRequestVO 代码生成器请求VO模型
     * @return 生成结果
     */
    @Override
    public String generate(CodeGeneratorRequestVO codeGeneratorRequestVO) {
        return null;
    }
}
