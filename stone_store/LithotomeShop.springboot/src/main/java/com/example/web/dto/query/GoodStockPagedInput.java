package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

/**
 * GoodStock查询模型
 */
@NoArgsConstructor
@Data
public class GoodStockPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 备注模糊查询条件
     */
  	 @JsonProperty("Remark")
    private String Remark;
     /**
     * 石材
     */
  	 @JsonProperty("GoodId")
    private Integer GoodId;
     /**
     * 店铺
     */
  	 @JsonProperty("ShopId")
    private Integer ShopId;

}
