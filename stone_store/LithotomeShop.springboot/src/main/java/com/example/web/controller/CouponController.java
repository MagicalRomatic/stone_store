package com.example.web.controller;

import com.example.web.dto.CouponDto;
import com.example.web.dto.query.CouponPagedInput;
import com.example.web.mapper.CouponMapper;
import com.example.web.service.CouponService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠卷控制器
 */
@RestController()
@RequestMapping("/Coupon")
public class CouponController {
    @Autowired()
    private CouponService _CouponService;
    @Autowired()
    private CouponMapper _CouponMapper;

    /**
     * 优惠卷分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouponDto> List(@RequestBody CouponPagedInput input) {
        return _CouponService.List(input);
    }

    /**
     * 单个优惠卷查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouponDto Get(@RequestBody CouponPagedInput input) {

        return _CouponService.Get(input);
    }

    /**
     * 优惠卷创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouponDto CreateOrEdit(@RequestBody CouponDto input) throws Exception {
        return _CouponService.CreateOrEdit(input);
    }

    /**
     * 优惠卷删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        _CouponService.Delete(input);
    }

    /**
     * 优惠卷批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        _CouponService.BatchDelete(input);
    }


}
