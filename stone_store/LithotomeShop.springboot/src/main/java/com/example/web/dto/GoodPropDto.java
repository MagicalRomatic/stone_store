package com.example.web.dto;

import com.example.web.entity.GoodProp;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 石材属性类
 */
@Data
public class GoodPropDto extends BaseDto
{

    
     
    /**
     * 属性名称
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 属性值
     */ 
    @JsonProperty("Value")
    private String Value;
    
     
    /**
     * 石材
     */ 
    @JsonProperty("GoodId")
    private Integer GoodId;          

     @JsonProperty("GoodDto") 
    private GoodDto GoodDto;                        
   
 	 /**
     * 把石材属性传输模型转换成石材属性实体
     */
    public GoodProp MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodProp GoodProp= new GoodProp();
        BeanUtils.copyProperties(GoodProp,this);
        return GoodProp;
    }

}
