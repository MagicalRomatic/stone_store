package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.GoodCollectDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
/**
 * 石材收藏表
 */
@Data
@TableName("`GoodCollect`")
public class GoodCollect extends BaseEntity {

      
    /**
     * 石材
     */  
    @JsonProperty("GoodId")
    @TableField(value="GoodId",updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;          
      
    /**
     * 收藏人
     */  
    @JsonProperty("CollectUserId")
    @TableField(value="CollectUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CollectUserId;          
  
    /**
     * 把石材收藏实体转换成石材收藏传输模型
     */
    public GoodCollectDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodCollectDto GoodCollectDto = new GoodCollectDto();
        BeanUtils.copyProperties(GoodCollectDto,this);
        return GoodCollectDto;
    }

}
