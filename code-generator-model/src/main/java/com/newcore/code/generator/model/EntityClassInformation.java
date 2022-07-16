package com.newcore.code.generator.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 实体信息模型
 * @author Zhou Chaowei
 * @date 2022-07-15
 */
@ApiModel(description = "实体信息模型",value = "EntityClassInformation")
@Data
public class EntityClassInformation implements Serializable {

    private static final long serialVersionUID = 4322362638706622387L;
    @ApiModelProperty(value = "实体模型类名称", dataType = "String", required = true)
    private String entityClassName;
    @ApiModelProperty(value = "实体模型类注释", dataType = "String", required = true)
    private String entityClassComment;
    @ApiModelProperty(value = "表名称", dataType = "String", required = true)
    private String tableName;
    @ApiModelProperty(value = "属性字段信息列表", dataType = "List", required = true)
    private List<FieldInformation> fieldList;
}
