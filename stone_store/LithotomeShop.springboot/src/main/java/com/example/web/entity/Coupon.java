package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouponDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
/**
 * 优惠卷表
 */
@Data
@TableName("`Coupon`")
public class Coupon extends BaseEntity {

      
  	  /**
     * 名称
     */  
    @JsonProperty("Title")
    @TableField(value="Title",updateStrategy = FieldStrategy.IGNORED)
    private String Title;
      
    /**
     * 使用限制
     */  
    @JsonProperty("UseMoney")
    @TableField(value="UseMoney",updateStrategy = FieldStrategy.IGNORED)
    private Double UseMoney;      
      
    /**
     * 面值
     */  
    @JsonProperty("Money")
    @TableField(value="Money",updateStrategy = FieldStrategy.IGNORED)
    private Double Money;      
      
    /**
     * 起始有效时间
     */  
    @JsonProperty("BeginActiveTime")
    @TableField(value="BeginActiveTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;  
    /**
     * 截至有效时间
     */  
    @JsonProperty("EndActiveTime")
    @TableField(value="EndActiveTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime; 
      
    /**
     * 总数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.IGNORED)
    private Integer Qty;          
      
    /**
     * 领取数量
     */  
    @JsonProperty("GetQty")
    @TableField(value="GetQty",updateStrategy = FieldStrategy.IGNORED)
    private Integer GetQty;


    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;


    /**
     * 把优惠卷实体转换成优惠卷传输模型
     */
    public CouponDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouponDto CouponDto = new CouponDto();
        BeanUtils.copyProperties(CouponDto,this);
        return CouponDto;
    }

}
