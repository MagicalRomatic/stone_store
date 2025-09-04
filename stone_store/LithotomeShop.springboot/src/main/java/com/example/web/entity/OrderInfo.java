package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.OrderInfoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@TableName("`OrderInfo`")
public class OrderInfo extends BaseEntity {


    /**
     * 订单编号
     */
    @JsonProperty("OrderNo")
    @TableField(value = "OrderNo", updateStrategy = FieldStrategy.IGNORED)
    private String OrderNo;

    /**
     * 用户
     */
    @JsonProperty("UserId")
    @TableField(value = "UserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;

    /**
     * 商家
     */
    @JsonProperty("ShopUserId")
    @TableField(value = "ShopUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopUserId;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value = "ShopId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

    /**
     * 总数量
     */
    @JsonProperty("TotalQty")
    @TableField(value = "TotalQty", updateStrategy = FieldStrategy.IGNORED)
    private Integer TotalQty;

    /**
     * 总金额
     */
    @JsonProperty("TotalMoney")
    @TableField(value = "TotalMoney", updateStrategy = FieldStrategy.IGNORED)
    private Double TotalMoney;


    /**
     * 原始金额
     */
    @JsonProperty("OrginMoney")
    @TableField(value = "OrginMoney", updateStrategy = FieldStrategy.IGNORED)
    private Double OrginMoney;

    /**
     * 折扣金额
     */
    @JsonProperty("DiscountMoney")
    @TableField(value = "DiscountMoney", updateStrategy = FieldStrategy.IGNORED)
    private Double DiscountMoney;

    /**
     * 支付类型
     */
    @JsonProperty("PayType")
    @TableField(value = "PayType", updateStrategy = FieldStrategy.IGNORED)
    private String PayType;

    /**
     * 支付时间
     */
    @JsonProperty("PayTime")
    @TableField(value = "PayTime", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime PayTime;


    /**
     * 过期时间
     */
    @JsonProperty("ExpireTime")
    @TableField(value = "ExpireTime", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime ExpireTime;


    /**
     * 支付订单号
     */
    @JsonProperty("PayOrderNo")
    @TableField(value = "PayOrderNo", updateStrategy = FieldStrategy.IGNORED)
    private String PayOrderNo;

    /**
     * 物流单号
     */
    @JsonProperty("LogisticsNo")
    @TableField(value = "LogisticsNo", updateStrategy = FieldStrategy.IGNORED)
    private String LogisticsNo;

    /**
     * 发货时间
     */
    @JsonProperty("DeliveryTime")
    @TableField(value = "DeliveryTime", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime DeliveryTime;

    /**
     * 收货时间
     */
    @JsonProperty("GetOrderTime")
    @TableField(value = "GetOrderTime", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime GetOrderTime;

    /**
     * 收货地址
     */
    @JsonProperty("ReceiveAddress")
    @TableField(value = "ReceiveAddress", updateStrategy = FieldStrategy.IGNORED)
    private String ReceiveAddress;

    /**
     * 收货电话
     */
    @JsonProperty("ReceivePhone")
    @TableField(value = "ReceivePhone", updateStrategy = FieldStrategy.IGNORED)
    private String ReceivePhone;

    /**
     * 收货人
     */
    @JsonProperty("ReceiveName")
    @TableField(value = "ReceiveName", updateStrategy = FieldStrategy.IGNORED)
    private String ReceiveName;


    /**
     * 订单状态
     */
    @JsonProperty("OrderStatus")
    @TableField(value = "OrderStatus", updateStrategy = FieldStrategy.IGNORED)
    private Integer OrderStatus;

    /**
     * 备注
     */
    @JsonProperty("Remark")
    @TableField(value = "Remark", updateStrategy = FieldStrategy.IGNORED)
    private String Remark;



    /**
     * 物流信息
     */
    @JsonProperty("ExpressInfo")
    @TableField(value = "ExpressInfo", updateStrategy = FieldStrategy.IGNORED)
    private String ExpressInfo;

    /**
     * 把订单实体转换成订单传输模型
     */
    public OrderInfoDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        OrderInfoDto OrderInfoDto = new OrderInfoDto();
        BeanUtils.copyProperties(OrderInfoDto, this);
        return OrderInfoDto;
    }

}
