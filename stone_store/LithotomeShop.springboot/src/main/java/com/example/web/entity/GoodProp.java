package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodPropDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 石材属性表
 */
@Data
@TableName("`GoodProp`")
public class GoodProp extends BaseEntity {

      
  	  /**
     * 属性名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
  	  /**
     * 属性值
     */  
    @JsonProperty("Value")
    @TableField(value="Value",updateStrategy = FieldStrategy.IGNORED)
    private String Value;
      
    /**
     * 石材
     */  
    @JsonProperty("GoodId")
    @TableField(value="GoodId",updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;          
  
    /**
     * 把石材属性实体转换成石材属性传输模型
     */
    public GoodPropDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodPropDto GoodPropDto = new GoodPropDto();
        BeanUtils.copyProperties(GoodPropDto,this);
        return GoodPropDto;
    }

}
