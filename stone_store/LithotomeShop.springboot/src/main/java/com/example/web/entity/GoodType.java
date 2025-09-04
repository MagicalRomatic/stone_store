package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodTypeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 石材分类表
 */
@Data
@TableName("`GoodType`")
public class GoodType extends BaseEntity {

      
  	  /**
     * 名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
    /**
     * 排序
     */  
    @JsonProperty("Sort")
    @TableField(value="Sort",updateStrategy = FieldStrategy.IGNORED)
    private Integer Sort;


    /**
     * 是否店铺
     */
    @JsonProperty("IsShop")
    @TableField(value="IsShop",updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsShop;

    /**
     * 店铺编号
     */
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;
  
    /**
     * 把石材分类实体转换成石材分类传输模型
     */
    public GoodTypeDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodTypeDto GoodTypeDto = new GoodTypeDto();
        BeanUtils.copyProperties(GoodTypeDto,this);
        return GoodTypeDto;
    }

}
