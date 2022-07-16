package com.newcore.code.generator.controller.api.impl;

import com.newcore.code.generator.controller.api.FreeMarkerParserService;
import com.newcore.code.generator.model.EntityClassInformation;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker转换服务接口实现类
 * @author Zhou Chaowei
 */
@Service("freeMarkerParserService")
public class FreeMarkerParserServiceImpl implements FreeMarkerParserService {

    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerParserServiceImpl.class);

    @Autowired
    private Configuration configuration;

    @Value("${freemarker.template.path}")
    private String templateName;

    /**
     * FreeMarker转换方法
     * @param entityClassInformation 实体信息模型
     * @param author         作者
     * @param packageRoute   包路径
     * @param returnTypeName 返回封装名称
     * @return
     */
    @Override
    public String freemarkerParser(EntityClassInformation entityClassInformation, String author, String packageRoute, String returnTypeName) {
        try {
            StringWriter stringWriter = new StringWriter();
            Map<String,Object> map = new HashMap<>(10);
            map.put("classInfo",entityClassInformation);
            map.put("authorName",author);
            map.put("packageName",packageRoute);
            map.put("returnUtils",returnTypeName);

            Template template = configuration.getTemplate(templateName);
            template.process(map,stringWriter);
            logger.info("FreeMarker转换方法 || 转换结果:{}", stringWriter);
            return stringWriter.toString();
        } catch (TemplateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
