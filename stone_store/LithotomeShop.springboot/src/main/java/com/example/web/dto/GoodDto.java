package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.entity.Good;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 石材类
 */
@Data
public class GoodDto extends BaseDto {

    /**
     * 名称
     */
    @JsonProperty("Name")
    private String Name;

    /**
     * 封面
     */
    @JsonProperty("Cover")
    private String Cover;

    /**
     * 主图
     */
    @JsonProperty("ImageUrls")
    private String ImageUrls;

    /**
     * 价格
     */
    @JsonProperty("Price")
    private Double Price;

    /**
     * 库存
     */
    @JsonProperty("Stock")
    private Double Stock;

    /**
     * 详情内容
     */
    @JsonProperty("Content")
    private String Content;

    /**
     * 所属店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 所属用户
     */
    @JsonProperty("UserId")
    private Integer UserId;

    /**
     * 单位
     */
    @JsonProperty("Unit")
    private String Unit;

    /**
     * 分类
     */
    @JsonProperty("GoodTypeId")
    private Integer GoodTypeId;

    @JsonProperty("GoodTypeDto")
    private GoodTypeDto GoodTypeDto;

    /**
     * 店铺分类
     */
    @JsonProperty("ShopGoodTypeId")
    @TableField(value = "ShopGoodTypeId", updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopGoodTypeId;
    /**
     * 店铺分类
     */
    @JsonProperty("ShopGoodTypeDto")
    private GoodTypeDto ShopGoodTypeDto;

    @JsonProperty("UserDto")
    private AppUserDto UserDto;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

    /**
     * 购买人次
     */
    @JsonProperty("BuyCount")
    private Integer BuyCount;

    /**
     * 库存下线
     */
    @JsonProperty("ReplenishmentLimit")
    private Integer ReplenishmentLimit;

    /**
     * 是否上架
     */
    @JsonProperty("IsOnSale")
    private Boolean IsOnSale;

    /**
     * 把石材传输模型转换成石材实体
     */
    public Good MapToEntity() throws InvocationTargetException, IllegalAccessException {
        Good Good = new Good();
        BeanUtils.copyProperties(Good, this);
        return Good;
    }

}
