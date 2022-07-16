package com.newcore.code.generator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代码生成器请求VO模型
 * @author Zhou Chaowei
 * @date 2022-07-15
 */
@ApiModel(description = "代码生成器请求VO模型",value = "CodeGeneratorRequestVO")
@Data
public class CodeGeneratorRequestVO implements Serializable {

    private static final long serialVersionUID = 7643151608037136555L;
    @ApiModelProperty(value = "待生成SQL语句代码", dataType = "String", required = true)
    private String tableSqlcode;
    @ApiModelProperty(value = "作者姓名", dataType = "String", required = true)
    private String authorName;
    @ApiModelProperty(value = "包路径", dataType = "String", required = true)
    private String packageRoute;
    @ApiModelProperty(value = "返回封装", dataType = "String", required = true)
    private String returnTypeName;

    @ApiModelProperty(value = "待生成控制器服务层代码", dataType = "String", required = true)
    private String serviceCode;
    @ApiModelProperty(value = "生成类型", dataType = "String", required = true)
    private String generateType;
}
