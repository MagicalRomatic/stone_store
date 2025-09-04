package com.example.web.dto;

import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章类
 */
@Data
public class ArticleDto extends BaseDto {


    /**
     * 标题
     */
    @JsonProperty("Title")
    private String Title;


    /**
     * 封面
     */
    @JsonProperty("Cover")
    private String Cover;


    /**
     * 内容
     */
    @JsonProperty("Content")
    private String Content;


    /**
     * 文章分类
     */
    @JsonProperty("ArticleTypeId")
    private Integer ArticleTypeId;
    /**
     * 是否置顶
     */
    @JsonProperty("IsTop")
    private Boolean IsTop;

    /**
     * 阅读数
     */
    @JsonProperty("ReadCount")
    private Integer ReadCount;

    @JsonProperty("ArticleTypeDto")
    private ArticleTypeDto ArticleTypeDto;

    /**
     * 把文章传输模型转换成文章实体
     */
    public Article MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Article Article = new Article();
        BeanUtils.copyProperties(Article, this);
        return Article;
    }

}
