package com.example.web.dto;

import com.example.web.entity.BuyCard;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 购物车类
 */
@Data
public class BuyCardDto extends BaseDto
{

    
     
    /**
     * 店铺
     */ 
    @JsonProperty("ShopId")
    private Integer ShopId;          
    
     
    /**
     * 所属用户
     */ 
    @JsonProperty("BelongUserId")
    private Integer BelongUserId;          
    
     
    /**
     * 石材
     */ 
    @JsonProperty("GoodId")
    private Integer GoodId;          
    
     
    /**
     * 数量
     */ 
    @JsonProperty("Qty")
    private Integer Qty;          

     @JsonProperty("ShopDto") 
    private ShopDto ShopDto;                        
   
     @JsonProperty("GoodDto") 
    private GoodDto GoodDto;                        
   
     @JsonProperty("BelongUserDto") 
    private AppUserDto BelongUserDto;                        
   
 	 /**
     * 把购物车传输模型转换成购物车实体
     */
    public BuyCard MapToEntity() throws InvocationTargetException, IllegalAccessException {
        BuyCard BuyCard= new BuyCard();
        BeanUtils.copyProperties(BuyCard,this);
        return BuyCard;
    }

}
