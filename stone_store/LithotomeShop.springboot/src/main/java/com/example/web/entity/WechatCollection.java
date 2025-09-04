package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 会话集合表
 */
@Data
@TableName("`WechatCollection`")
public class WechatCollection extends BaseEntity {

      
    /**
     * 本身用户
     */  
    @JsonProperty("SelfUserId")
    @TableField(value="SelfUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer SelfUserId;          
      
    /**
     * 对方用户
     */  
    @JsonProperty("OtherUserId")
    @TableField(value="OtherUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer OtherUserId;          
      
  	  /**
     * 最新消息
     */  
    @JsonProperty("LastMessage")
    @TableField(value="LastMessage",updateStrategy = FieldStrategy.IGNORED)
    private String LastMessage;
      
    /**
     * 最新消息时间
     */  
    @JsonProperty("LastTime")
    @TableField(value="LastTime",updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime LastTime;             
  
    /**
     * 把会话集合实体转换成会话集合传输模型
     */
    public WechatCollectionDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        WechatCollectionDto WechatCollectionDto = new WechatCollectionDto();
        BeanUtils.copyProperties(WechatCollectionDto,this);
        return WechatCollectionDto;
    }

}
