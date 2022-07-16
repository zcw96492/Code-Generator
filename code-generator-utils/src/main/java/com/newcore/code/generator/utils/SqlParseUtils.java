package com.newcore.code.generator.utils;

import com.newcore.code.generator.model.EntityClassInformation;
import com.newcore.code.generator.model.FieldInformation;
import com.newcore.code.generator.utils.enums.BusinessExceptionCodeEnum;
import com.newcore.code.generator.utils.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sql转换工具类
 * @author zhouchaowei
 */
public class SqlParseUtils {

    private static final Logger logger = LoggerFactory.getLogger(SqlParseUtils.class);
    private static final String TABLE_UPPER_CASE = "TABLE";
    private static final String TABLE_LOWER_CASE = "table";
    private static final String IF_NOT_EXISTS_UPPER_CASE = "IF NOT EXISTS";
    private static final String IF_NOT_EXISTS_LOWER_CASE = "if not exists";
    private static final String PARENTHESES_LEFT = "(";
    private static final String PARENTHESES_RIGHT = ")";
    private static final String SEPARATOR_COMMA = "`";
    private static final String SEPARATOR_SYMBOL = "`.`";
    private static final String UNDERLINE = "_";
    private static final String WHITE_SPACE = " ";
    private static final String COMMENT_SQL_LOWER_CASE = "comment=";
    private static final String COMMENT_SQL_UPPER_CASE = "COMMENT=";
    private static final String COMMENT_ORACLE_SQL_LOWER_CASE = "comment on table";
    private static final String COMMENT_ORACLE_SQL_UPPER_CASE = "COMMENT ON TABLE";

    /**
     * 转换SQL语句
     * @param completeTableSql 完整SQL语句
     * @return
     */
    public static EntityClassInformation parseTableSql(String completeTableSql){
        /* 1.定义返回结构体 */
        EntityClassInformation entityClassInformation = new EntityClassInformation();

        /* 2.对Sql中的特殊符号,中文符号,换行符,引号进行转换和去空格 */
        completeTableSql = completeTableSql.trim()
                .replaceAll("'",SEPARATOR_COMMA)
                .replaceAll("\"",SEPARATOR_COMMA)
                .replaceAll("，",",")
                .toLowerCase();

        /* 3.解析表名,并将表名转换为类名 */
        String entityClassName = parseTableNameToEntityClassName(completeTableSql);

        /* 4.解析表释义,并将表释义转换为类注释 */
        String entityClassComment = parseTableCommentToEntityClassComment(completeTableSql,entityClassName);

        /* 5.解析表字段,并将表字段转换为实体属性 */
        List<FieldInformation> fieldList = parseTableColumnToField(completeTableSql);

        /* 6.组织返回参数 */
        entityClassInformation.setEntityClassName(entityClassName);
        entityClassInformation.setEntityClassComment(entityClassComment);
        entityClassInformation.setFieldList(fieldList);
        return entityClassInformation;
    }

    /**
     * 3.解析表名,并将表名转换为类名
     * @param completeTableSql 完整SQL语句
     * @return 类名
     */
    private static String parseTableNameToEntityClassName(String completeTableSql) {
        String tableName;

        /* 3.1.分离表名 */
        if(completeTableSql.contains(TABLE_UPPER_CASE) && completeTableSql.contains(PARENTHESES_LEFT)){
            tableName = completeTableSql.substring(completeTableSql.indexOf(TABLE_UPPER_CASE) + 5,completeTableSql.indexOf(PARENTHESES_LEFT));
        }else if(completeTableSql.contains(TABLE_LOWER_CASE) && completeTableSql.contains(PARENTHESES_LEFT)){
            tableName = completeTableSql.substring(completeTableSql.indexOf(TABLE_LOWER_CASE) + 5,completeTableSql.indexOf(PARENTHESES_LEFT));
        }else{
            logger.info("SQL语句结构有误,请核实后再试! || SQL语句:{}",completeTableSql);
            throw new BusinessException(BusinessExceptionCodeEnum.ERROR_0004.getCode(),BusinessExceptionCodeEnum.ERROR_0004.getMessage());
        }

        /* 3.2.新增对包含IF NOT EXISTS 的处理 */
        if(tableName.contains(IF_NOT_EXISTS_UPPER_CASE)){
            tableName = tableName.replaceAll(IF_NOT_EXISTS_UPPER_CASE,"");
        }
        if(tableName.contains(IF_NOT_EXISTS_LOWER_CASE)){
            tableName = tableName.replaceAll(IF_NOT_EXISTS_LOWER_CASE,"");
        }

        /* 3.3.处理表名上带"`"的情况 */
        if(tableName.contains(SEPARATOR_COMMA)){
            tableName = tableName.substring(tableName.indexOf(SEPARATOR_COMMA) + 1,tableName.indexOf(SEPARATOR_COMMA));
        }else{
            /* 空格开头的,需要替换掉\n,\t */
            tableName = tableName
                    .replaceAll(" ","")
                    .replaceAll("\n","")
                    .replaceAll("\t","");
        }

        /* 3.4.处理表名中带"."的情况 */
        if(tableName.contains(SEPARATOR_SYMBOL)){
            tableName = tableName.substring(tableName.indexOf(SEPARATOR_SYMBOL) + 3);
        }else{
            tableName = tableName.substring(tableName.indexOf(".") + 1);
        }

        /* 3.5.将表名转换为类名 */
        String className = StringCustomUtils.toUpperCaseForFirstWord(tableName);
        if(className.contains(UNDERLINE)){
            className = className.replaceAll(UNDERLINE,"");
        }
        return className;
    }

    /**
     * 4.解析表释义,并将表释义转换为类注释
     * @param completeTableSql 完整SQL语句
     * @param className 类名
     * @return 类注释
     */
    private static String parseTableCommentToEntityClassComment(String completeTableSql,String className) {
        String classComment;
        /* MySQL格式语句 */
        if(completeTableSql.contains(COMMENT_SQL_LOWER_CASE)){
            String classCommentTemp = completeTableSql
                    .substring(completeTableSql.lastIndexOf(COMMENT_SQL_LOWER_CASE) + 8)
                    .replaceAll(SEPARATOR_COMMA,"")
                    .trim();

            if(classCommentTemp.indexOf(WHITE_SPACE) != classCommentTemp.lastIndexOf(WHITE_SPACE)){
                classCommentTemp = classCommentTemp.substring(classCommentTemp.indexOf(WHITE_SPACE) + 1,classCommentTemp.lastIndexOf(WHITE_SPACE));
            }

            if(StringUtils.isNotBlank(classCommentTemp) && classCommentTemp.trim().length() > 0){
                classComment = classCommentTemp;
            }else{
                /* 修复表备注为空的问题 */
                classComment = className;
            }
        /* Oracle格式语句 */
        } else if (completeTableSql.contains(COMMENT_SQL_UPPER_CASE)) {
            String classCommentTemp = completeTableSql
                    .substring(completeTableSql.lastIndexOf(COMMENT_SQL_UPPER_CASE) + 8)
                    .replaceAll(SEPARATOR_COMMA,"")
                    .trim();

            if(classCommentTemp.indexOf(WHITE_SPACE) != classCommentTemp.lastIndexOf(WHITE_SPACE)){
                classCommentTemp = classCommentTemp.substring(classCommentTemp.indexOf(WHITE_SPACE) + 1,classCommentTemp.lastIndexOf(WHITE_SPACE));
            }

            if(StringUtils.isNotBlank(classCommentTemp) && classCommentTemp.trim().length() > 0){
                classComment = classCommentTemp;
            }else{
                /* 修复表备注为空的问题 */
                classComment = className;
            }
        /* MySQL格式语句 */
        } else if (completeTableSql.contains(COMMENT_ORACLE_SQL_LOWER_CASE)) {
            String classCommentTemp = completeTableSql.substring(completeTableSql.lastIndexOf(COMMENT_ORACLE_SQL_LOWER_CASE) + 17).trim();
            if(classCommentTemp.contains(SEPARATOR_COMMA)){
                classComment = classCommentTemp.substring(classCommentTemp.indexOf(SEPARATOR_COMMA) + 1).substring(0,classCommentTemp.indexOf(SEPARATOR_COMMA));
            }else{
                /* 修复表备注为空的问题 */
                classComment = className;
            }
        /* Oracle格式语句 */
        } else if (completeTableSql.contains(COMMENT_ORACLE_SQL_UPPER_CASE)) {
            String classCommentTemp = completeTableSql.substring(completeTableSql.lastIndexOf(COMMENT_ORACLE_SQL_UPPER_CASE) + 17).trim();
            if(classCommentTemp.contains(SEPARATOR_COMMA)){
                classComment = classCommentTemp.substring(classCommentTemp.indexOf(SEPARATOR_COMMA) + 1).substring(0,classCommentTemp.indexOf(SEPARATOR_COMMA));
            }else{
                /* 修复表备注为空的问题 */
                classComment = className;
            }
        } else {
            /* 修复表备注为空的问题 */
            classComment = className;
        }
        return classComment.replaceAll(";","");
    }

    private static final Pattern PATTERN_1 = Pattern.compile("comment `(.*?)\\`");

    /**
     * 5.解析表字段,并将表字段转换为实体属性
     * @param completeTableSql 完整SQL语句
     * @return 属性列表
     */
    private static List<FieldInformation> parseTableColumnToField(String completeTableSql) {
        /* 5.1.定义返回结构体 */
        List<FieldInformation> fieldInformationList = new ArrayList<>();

        /* 5.2.去除括号内的一段内容 */
        String fieldList = completeTableSql.
                substring(completeTableSql.indexOf(PARENTHESES_LEFT) + 1,completeTableSql.lastIndexOf(PARENTHESES_RIGHT));

        /* 5.3.匹配Comment,替换备注里的小逗号,防止不小心被当成切割符号切割 */
        Matcher matcher = PATTERN_1.matcher(fieldList);
        while(matcher.find()){
            String commentTemp = matcher.group();
            if(commentTemp.contains(",")){
                String commentTempFinal = commentTemp.replaceAll(",","，");
                fieldList = fieldList.replace(matcher.group(),commentTempFinal);
            }
        }

        /* 5.4. */

        /* 5.5. */

        /* 5.6. */
        String[] columnLineLevel = fieldList.split(",");
        if(columnLineLevel.length > 0){
            for (int i = 0 ; i < columnLineLevel.length ; i++) {
                String column = columnLineLevel[i];
                column.replaceAll("\n","").replaceAll("\t","").trim();

                boolean specialFlag = (!column.contains("constraint") && !column.contains("using") && !column.contains("unique") &&
                        !(column.contains("primary") && column.indexOf("storage") + 3 > column.indexOf("(")) &&
                        !column.contains("pctincrease") && !column.contains("buffer_pool") && !column.contains("tablespace") &&
                        !(column.contains("primary") && i > 3));

                if(specialFlag){
                    if(column.length() < 5){
                        continue;
                    }

                    String columnName = "";
                    column = column
                            .replaceAll(SEPARATOR_COMMA,WHITE_SPACE)
                            .replaceAll("\"",WHITE_SPACE)
                            .replaceAll("'","")
                            .replaceAll("  ",WHITE_SPACE)
                            .trim();

                    columnName = column.substring(0,column.indexOf(WHITE_SPACE));

                    String fieldName = StringCustomUtils.toLowerCaseForFirstWord(StringCustomUtils.underLineToCamelCase(columnName));
                    if(fieldName.contains("_")){
                        fieldName = fieldName.replaceAll("_","");
                    }

                    column = column.substring(column.indexOf(SEPARATOR_COMMA) + 1).trim();
                    String fieldClass;
                    if(column.contains("int") || column.contains("tinyint") || column.contains("smallint")){
                        fieldClass = Integer.class.getSimpleName();
                    }else if (column.contains("bigint")){
                        fieldClass = Long.class.getSimpleName();
                    }else if (column.contains("float")){
                        fieldClass = Float.class.getSimpleName();
                    }else if (column.contains("double")){
                        fieldClass = Double.class.getSimpleName();
                    }else if (column.contains("date") || column.contains("datetime") || column.contains("timestamp")){
                        fieldClass = Date.class.getSimpleName();
                    }else if (column.contains("clob")){
                        fieldClass = Clob.class.getSimpleName();
                    }else if (column.contains("blob")){
                        fieldClass = Blob.class.getSimpleName();
                    }else if (column.contains("varchar2") || column.contains("varchar") || column.contains("text") || column.contains("char") || column.contains("json")){
                        fieldClass = String.class.getSimpleName();
                        if(column.contains("varchar2") || column.contains("varchar")){
                            // TODO
                        }
                    }else if (column.contains("decimal") || column.contains("number")){
                        fieldClass = Float.class.getSimpleName();
                    }else{
                        fieldClass = String.class.getSimpleName();
                    }

                    // TODO





                }
            }
        }

        /* 5.7. */

        /* 5.10.返回类型 */
        return fieldInformationList;
    }
}
