package com.example.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.CouponRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠卷记录表对应的Mapper
 */
@Mapper
public interface CouponRecordMapper  extends BaseMapper<CouponRecord> {

}
