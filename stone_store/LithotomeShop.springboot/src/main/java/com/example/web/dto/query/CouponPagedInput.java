package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Coupon查询模型
 */
@NoArgsConstructor
@Data
public class CouponPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 名称模糊查询条件
     */
  	 @JsonProperty("Title")
    private String Title;
    /**
     * 有效时间时间范围
     */
    @JsonProperty("ActiveTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ActiveTimeRange;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 得到可以领取的
     */
    @JsonProperty("GetCanAppoint")
    private Boolean GetCanAppoint;

}
