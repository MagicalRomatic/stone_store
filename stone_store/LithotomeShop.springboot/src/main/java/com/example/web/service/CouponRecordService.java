package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.CouponRecordDto;
import com.example.web.dto.query.CouponRecordPagedInput;
import com.example.web.entity.CouponRecord;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 优惠卷记录功能的Service接口的定义清单
 */
public interface CouponRecordService extends IService<CouponRecord> {

    /**
     * 得到可以使用的优惠卷
     */
    List<CouponRecordDto> GetCanUseCoupon(CouponRecordPagedInput input) throws InvocationTargetException, IllegalAccessException;

    /**
     * 优惠卷记录的分页查询方法接口定义
     */
    public PagedResult<CouponRecordDto> List(CouponRecordPagedInput input) ;
    /**
     * 优惠卷记录的新增或者修改方法接口定义
     */
    public CouponRecordDto CreateOrEdit(CouponRecordDto input);

     /**
     * 获取优惠卷记录信息
     */
    public CouponRecordDto Get(CouponRecordPagedInput input);
 	 /**
     * 优惠卷记录删除
     */
    public void Delete(IdInput input);

    /**
     * 优惠卷记录批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
