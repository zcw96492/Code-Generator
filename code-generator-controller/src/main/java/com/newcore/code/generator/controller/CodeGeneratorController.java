package com.newcore.code.generator.controller;

import com.alibaba.fastjson.JSON;
import com.newcore.code.generator.controller.api.CommonGeneratorService;
import com.newcore.code.generator.controller.api.ModelGeneratorService;
import com.newcore.code.generator.controller.api.PersistenceGeneratorService;
import com.newcore.code.generator.controller.api.ViewGeneratorService;
import com.newcore.code.generator.model.CodeGeneratorRequestVO;
import com.newcore.code.generator.utils.RestServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 代码生成控制器
 * @author Zhou Chaowei
 * @date 2022-07-15
 */
@Controller
@RequestMapping("codeGenerator")
public class CodeGeneratorController {

    private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorController.class);

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private ModelGeneratorService modelGeneratorService;

    @Autowired
    private PersistenceGeneratorService persistenceGeneratorService;

    @Autowired
    private ViewGeneratorService viewGeneratorService;

    @Autowired
    private CommonGeneratorService commonGeneratorService;

    /**
     * 生成持久层代码放啊
     * @param codeGeneratorRequestVO 代码生成请求VO模型
     * @return 提示信息
     */
    @PostMapping("generate")
    public RestServerResponse<String> generate(@RequestBody CodeGeneratorRequestVO codeGeneratorRequestVO) {
        logger.info("代码生成器 || 生成持久层代码方法 || 入参:{}", JSON.toJSONString(codeGeneratorRequestVO));
        String generateResult;
        switch(codeGeneratorRequestVO.getGenerateType()){
            case "MODEL" :
                generateResult = modelGeneratorService.generate(codeGeneratorRequestVO);
                break;
            case "DAO" :
                generateResult = persistenceGeneratorService.generate(codeGeneratorRequestVO);
                break;
            case "CONTROLLER" :
                generateResult = viewGeneratorService.generate(codeGeneratorRequestVO);
                break;
            default:
                generateResult = commonGeneratorService.generate(codeGeneratorRequestVO);
                break;
        }
        return RestServerResponse.createBySuccessData(generateResult,serviceName);
    }
}
