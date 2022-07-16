package com.newcore.code.generator.controller.api.impl;

import com.newcore.code.generator.controller.api.FreeMarkerParserService;
import com.newcore.code.generator.controller.api.ModelGeneratorService;
import com.newcore.code.generator.model.CodeGeneratorRequestVO;
import com.newcore.code.generator.model.EntityClassInformation;
import com.newcore.code.generator.utils.SqlParseUtils;
import com.newcore.code.generator.utils.enums.BusinessExceptionCodeEnum;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 模型层代码生成服务接口实现类
 * @author Zhou Chaowei
 */
@Service("modelGeneratorService")
public class ModelGeneratorServiceImpl implements ModelGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(ModelGeneratorServiceImpl.class);

    @Value("${entity.generate.path}")
    private String entityGeneratePath;

    @Autowired
    private FreeMarkerParserService freeMarkerParserService;

    /**
     * 代码生成方法
     * @param codeGeneratorRequestVO 代码生成器请求VO模型
     * @return 生成结果
     */
    @Override
    public String generate(CodeGeneratorRequestVO codeGeneratorRequestVO) {
        /* 1.定义出参结果 */
        String result;

        /* 2.校验入参方法 */
        result = checkRequestParam(codeGeneratorRequestVO);

        /* 3.拆解SQL语句,支持传入多条CREATE SQL语句 */
        List<String> tableSqlLst = Arrays.asList(codeGeneratorRequestVO.getTableSqlcode().split("CREATE TABLE"));
        List<String> completeTableSqlLst = new ArrayList<>();
        tableSqlLst.forEach(tableSql ->{
            completeTableSqlLst.add("CREATE TABLE " + tableSql);
        });
        completeTableSqlLst.remove(0);

        /* 4.解析SQL */
        for (String completeTableSql : completeTableSqlLst){
            try{
                EntityClassInformation entityClassInformation = SqlParseUtils.parseTableSql(completeTableSql);

                String fileBinary = freeMarkerParserService.freemarkerParser(
                        entityClassInformation,
                        codeGeneratorRequestVO.getAuthorName(),
                        codeGeneratorRequestVO.getPackageRoute(),
                        codeGeneratorRequestVO.getReturnTypeName()
                );

                if(!Files.exists(Paths.get(entityGeneratePath))){
                    Files.createDirectories(Paths.get(entityGeneratePath));
                }

                Path path = Paths.get(entityGeneratePath + entityClassInformation.getEntityClassName() + "PO.java");
                Files.deleteIfExists(path);
                Files.createFile(path);
                Files.write(path,fileBinary.getBytes(StandardCharsets.UTF_8));
            }catch (IOException e){
                logger.error("代码生成器 || 实体文件生成异常!",e);
            }
        }

        /* 5.返回结果 */
        return result;
    }

    /**
     * 2.校验入参方法
     * @param codeGeneratorRequestVO 代码生成器请求VO模型
     */
    private String checkRequestParam(CodeGeneratorRequestVO codeGeneratorRequestVO) {
        if(StringUtils.isBlank(codeGeneratorRequestVO.getTableSqlcode())){
            return BusinessExceptionCodeEnum.ERROR_0001.getMessage();
        }else if(StringUtils.isBlank(codeGeneratorRequestVO.getPackageRoute())){
            return BusinessExceptionCodeEnum.ERROR_0002.getMessage();
        }else if(StringUtils.isBlank(codeGeneratorRequestVO.getReturnTypeName())){
            return BusinessExceptionCodeEnum.ERROR_0003.getMessage();
        }else{
            return null;
        }
    }
}
