package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 库存记录表
 */
@Data
@TableName("`GoodStock`")
public class GoodStock extends BaseEntity {

      
    /**
     * 石材
     */  
    @JsonProperty("GoodId")
    @TableField(value="GoodId",updateStrategy = FieldStrategy.IGNORED)
    private Integer GoodId;          
      
    /**
     * 调整数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.IGNORED)
    private Double Qty;      
      
    /**
     * 店铺
     */  
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;          
      
  	  /**
     * 备注
     */  
    @JsonProperty("Remark")
    @TableField(value="Remark",updateStrategy = FieldStrategy.IGNORED)
    private String Remark;
  
    /**
     * 把库存记录实体转换成库存记录传输模型
     */
    public GoodStockDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        GoodStockDto GoodStockDto = new GoodStockDto();
        BeanUtils.copyProperties(GoodStockDto,this);
        return GoodStockDto;
    }

}
