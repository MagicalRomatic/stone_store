package com.example.web.dto;

import com.example.web.entity.GoodCollect;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 石材收藏类
 */
@Data
public class GoodCollectDto extends BaseDto
{

    
     
    /**
     * 石材
     */ 
    @JsonProperty("GoodId")
    private Integer GoodId;          
    
     
    /**
     * 收藏人
     */ 
    @JsonProperty("CollectUserId")
    private Integer CollectUserId;          

     @JsonProperty("CollectUserDto") 
    private AppUserDto CollectUserDto;                        
   
     @JsonProperty("GoodDto") 
    private GoodDto GoodDto;                        
   
 	 /**
     * 把石材收藏传输模型转换成石材收藏实体
     */
    public GoodCollect MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodCollect GoodCollect= new GoodCollect();
        BeanUtils.copyProperties(GoodCollect,this);
        return GoodCollect;
    }

}
