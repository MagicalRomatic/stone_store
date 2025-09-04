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
 * 文章类型类
 */
@Data
public class ArticleTypeDto extends BaseDto
{

    
     
    /**
     * 类型
     */ 
    @JsonProperty("Name")
    private String Name;
    
  
    
     
    /**
     * 顺序
     */ 
    @JsonProperty("Sort")
    private Integer Sort;          
 

 	 /**
     * 把文章类型传输模型转换成文章类型实体
     */
    public ArticleType MapToEntity() throws InvocationTargetException, IllegalAccessException {
        ArticleType ArticleType= new ArticleType();
        BeanUtils.copyProperties(ArticleType,this);
        return ArticleType;
    }

}
