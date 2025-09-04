package com.example.web.dto;

import com.example.web.entity.Coupon;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;
/**
 * 优惠卷类
 */
@Data
public class CouponDto extends BaseDto
{

    
     
    /**
     * 名称
     */ 
    @JsonProperty("Title")
    private String Title;
    
     
    /**
     * 使用限制
     */ 
    @JsonProperty("UseMoney")
    private Double UseMoney;      
    
     
    /**
     * 面值
     */ 
    @JsonProperty("Money")
    private Double Money;      
    
     
    /**
     * 起始有效时间
     */  
    @JsonProperty("BeginActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;  
    /**
     * 截至有效时间
     */  
    @JsonProperty("EndActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime; 
    
     
    /**
     * 总数量
     */ 
    @JsonProperty("Qty")
    private Integer Qty;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;




    /**
     * 领取数量
     */ 
    @JsonProperty("GetQty")
    private Integer GetQty;


    /**
     * 选中的用户
     */
    @JsonProperty("UserIds")
    private List<Integer> UserIds;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

 	 /**
     * 把优惠卷传输模型转换成优惠卷实体
     */
    public Coupon MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Coupon Coupon= new Coupon();
        BeanUtils.copyProperties(Coupon,this);
        return Coupon;
    }

}
