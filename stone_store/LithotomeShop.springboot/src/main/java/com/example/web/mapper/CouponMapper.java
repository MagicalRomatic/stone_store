package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠卷表对应的Mapper
 */
@Mapper
public interface CouponMapper  extends BaseMapper<Coupon> {

}
