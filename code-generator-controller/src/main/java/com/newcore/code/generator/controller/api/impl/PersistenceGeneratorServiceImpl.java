package com.newcore.code.generator.controller.api.impl;

import com.newcore.code.generator.controller.api.PersistenceGeneratorService;
import com.newcore.code.generator.model.CodeGeneratorRequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 持久层生成服务接口实现类
 * @author Zhou Chaowei
 */
@Service("persistenceGeneratorService")
public class PersistenceGeneratorServiceImpl implements PersistenceGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceGeneratorServiceImpl.class);

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
