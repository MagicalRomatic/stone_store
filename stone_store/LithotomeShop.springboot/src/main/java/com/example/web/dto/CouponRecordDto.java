package com.example.web.dto;

import com.example.web.entity.CouponRecord;
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

/**
 * 优惠卷记录类
 */
@Data
public class CouponRecordDto extends BaseDto {


    /**
     * 优惠卷
     */
    @JsonProperty("CouponId")
    private Integer CouponId;


    /**
     * 领取人
     */
    @JsonProperty("GetUserId")
    private Integer GetUserId;


    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;


    @JsonProperty("ShopDto")
    private ShopDto ShopDto;
    /**
     * 起始有效时间
     */
    @JsonProperty("BeginActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime BeginActiveTime;
    /**
     * 截至有效时间
     */
    @JsonProperty("EndActiveTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime EndActiveTime;


    /**
     * 使用时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("UseTime")
    private LocalDateTime UseTime;


    /**
     * 关联单号
     */
    @JsonProperty("RelativeNo")
    private String RelativeNo;

    @JsonProperty("CouponDto")
    private CouponDto CouponDto;

    @JsonProperty("GetUserDto")
    private AppUserDto GetUserDto;

    /**
     * 把优惠卷记录传输模型转换成优惠卷记录实体
     */
    public CouponRecord MapToEntity() throws InvocationTargetException, IllegalAccessException {
        CouponRecord CouponRecord = new CouponRecord();
        BeanUtils.copyProperties(CouponRecord, this);
        return CouponRecord;
    }

}
