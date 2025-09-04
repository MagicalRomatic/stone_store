package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

import java.time.LocalDateTime;

import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;

/**
 * 文章表
 */
@Data
@TableName("`Article`")
public class Article extends BaseEntity {


    /**
     * 标题
     */
    @JsonProperty("Title")
    @TableField(value = "Title", updateStrategy = FieldStrategy.IGNORED)
    private String Title;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    @TableField(value = "Cover", updateStrategy = FieldStrategy.IGNORED)
    private String Cover;

    /**
     * 内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 文章分类
     */
    @JsonProperty("ArticleTypeId")
    @TableField(value = "ArticleTypeId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ArticleTypeId;

    /**
     * 阅读数
     */
    @JsonProperty("ReadCount")
    @TableField(value = "ReadCount", updateStrategy = FieldStrategy.IGNORED)
    private Integer ReadCount;
    /**
     * 是否置顶
     */
    @JsonProperty("IsTop")
    @TableField(value = "IsTop", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsTop;

    /**
     * 把文章实体转换成文章传输模型
     */
    public ArticleDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        ArticleDto ArticleDto = new ArticleDto();
        BeanUtils.copyProperties(ArticleDto, this);
        return ArticleDto;
    }

}
