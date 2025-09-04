package com.example.web.dto;
import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
/**
 * 库存记录类
 */
@Data
public class GoodStockDto extends BaseDto
{

    
     
    /**
     * 石材
     */ 
    @JsonProperty("GoodId")
    private Integer GoodId;          
    
     
    /**
     * 调整数量
     */ 
    @JsonProperty("Qty")
    private Double Qty;      
    
     
    /**
     * 店铺
     */ 
    @JsonProperty("ShopId")
    private Integer ShopId;          
    
     
    /**
     * 备注
     */ 
    @JsonProperty("Remark")
    private String Remark;

     @JsonProperty("GoodDto") 
    private GoodDto GoodDto;                        
   
     @JsonProperty("ShopDto") 
    private ShopDto ShopDto;                        
   
 	 /**
     * 把库存记录传输模型转换成库存记录实体
     */
    public GoodStock MapToEntity() throws InvocationTargetException, IllegalAccessException {
        GoodStock GoodStock= new GoodStock();
        BeanUtils.copyProperties(GoodStock,this);
        return GoodStock;
    }

}
