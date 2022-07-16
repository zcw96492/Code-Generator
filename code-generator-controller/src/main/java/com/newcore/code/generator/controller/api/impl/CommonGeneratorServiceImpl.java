package com.newcore.code.generator.controller.api.impl;

import com.newcore.code.generator.controller.api.CommonGeneratorService;
import com.newcore.code.generator.controller.api.ModelGeneratorService;
import com.newcore.code.generator.controller.api.PersistenceGeneratorService;
import com.newcore.code.generator.model.CodeGeneratorRequestVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通用代码生成服务接口实现类
 * @author Zhou Chaowei
 */
@Service("commonGeneratorService")
public class CommonGeneratorServiceImpl implements CommonGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(CommonGeneratorServiceImpl.class);

    @Autowired
    private ModelGeneratorService modelGeneratorService;

    @Autowired
    private PersistenceGeneratorService persistenceGeneratorService;

    /**
     * 代码生成方法
     * @param codeGeneratorRequestVO 代码生成器请求VO模型
     * @return 生成结果
     */
    @Override
    public String generate(CodeGeneratorRequestVO codeGeneratorRequestVO) {
        String parseResult = "代码生成失败!";
        String modelGenerateResult = modelGeneratorService.generate(codeGeneratorRequestVO);
        String persistenceGenerateResult = persistenceGeneratorService.generate(codeGeneratorRequestVO);
        if(StringUtils.isNotBlank(modelGenerateResult) && StringUtils.isNotBlank(persistenceGenerateResult)){
            logger.info("通用生成方法 || 实体生成结果:{},持久层生成结果:{}",modelGenerateResult,persistenceGenerateResult);
            parseResult = "代码生成成功!";
        }
        return parseResult;
    }
}
