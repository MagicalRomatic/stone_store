package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.CouponRecordDto;
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
 * 优惠卷记录表
 */
@Data
@TableName("`CouponRecord`")
public class CouponRecord extends BaseEntity {


    /**
     * 优惠卷
     */
    @JsonProperty("CouponId")
    @TableField(value="CouponId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CouponId;

    /**
     * 领取人
     */
    @JsonProperty("GetUserId")
    @TableField(value="GetUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer GetUserId;

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
     * 使用时间
     */
    @JsonProperty("UseTime")
    @TableField(value="UseTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime UseTime;

    /**
     * 关联单号
     */
    @JsonProperty("RelativeNo")
    @TableField(value="RelativeNo",updateStrategy = FieldStrategy.IGNORED)
    private String RelativeNo;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

    /**
     * 把优惠卷记录实体转换成优惠卷记录传输模型
     */
    public CouponRecordDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        CouponRecordDto CouponRecordDto = new CouponRecordDto();
        BeanUtils.copyProperties(CouponRecordDto,this);
        return CouponRecordDto;
    }

}
