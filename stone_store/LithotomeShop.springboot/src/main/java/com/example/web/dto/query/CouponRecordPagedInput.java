package com.example.web.dto.query;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * CouponRecord查询模型
 */
@NoArgsConstructor
@Data
public class CouponRecordPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 关联单号模糊查询条件
     */
    @JsonProperty("RelativeNo")
    private String RelativeNo;
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
     * 使用时间时间范围
     */
    @JsonProperty("UseTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> UseTimeRange;
    /**
     * 有效时间时间范围
     */
    @JsonProperty("ActiveTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> ActiveTimeRange;

    @JsonProperty("ShopIds")
    private List<Integer> ShopIds;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    @TableField(value="ShopId",updateStrategy = FieldStrategy.IGNORED)
    private Integer ShopId;

}
