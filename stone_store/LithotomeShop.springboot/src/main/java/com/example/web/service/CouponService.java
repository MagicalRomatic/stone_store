package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouponDto;
import com.example.web.dto.query.CouponPagedInput;
import com.example.web.entity.Coupon;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 优惠卷功能的Service接口的定义清单
 */
public interface CouponService extends IService<Coupon> {

    /**
     * 优惠卷的分页查询方法接口定义
     */
    public PagedResult<CouponDto> List(CouponPagedInput input) ;
    /**
     * 优惠卷的新增或者修改方法接口定义
     */
    public CouponDto CreateOrEdit(CouponDto input);

     /**
     * 获取优惠卷信息
     */
    public CouponDto Get(CouponPagedInput input);
 	 /**
     * 优惠卷删除
     */
    public void Delete(IdInput input);

    /**
     * 优惠卷批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
