package com.newcore.code.generator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性信息模型
 * @author Zhou Chaowei
 * @date 2022-07-15
 */
@ApiModel(description = "属性信息模型",value = "FieldInformation")
@Data
public class FieldInformation implements Serializable {

    private static final long serialVersionUID = -4531779164458098907L;
    @ApiModelProperty(value = "字段名称", dataType = "String", required = true)
    private String columnName;
    @ApiModelProperty(value = "字段类型", dataType = "String", required = true)
    private String columnType;
    @ApiModelProperty(value = "字段长度", dataType = "String", required = true)
    private String columnLength;
    @ApiModelProperty(value = "属性名称", dataType = "String", required = true)
    private String fieldName;
    @ApiModelProperty(value = "属性数据类型", dataType = "String", required = true)
    private String fieldType;
    @ApiModelProperty(value = "属性注释", dataType = "String", required = true)
    private String fieldComment;
    @ApiModelProperty(value = "属性长度", dataType = "String", required = true)
    private String fieldLength;
}
