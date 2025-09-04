package com.example.web.controller;

import com.example.web.dto.CouponRecordDto;
import com.example.web.dto.query.CouponRecordPagedInput;
import com.example.web.mapper.CouponRecordMapper;
import com.example.web.service.CouponRecordService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 优惠卷记录控制器
 */
@RestController()
@RequestMapping("/CouponRecord")
public class CouponRecordController {
    @Autowired()
    private CouponRecordService _CouponRecordService;
    @Autowired()
    private CouponRecordMapper _CouponRecordMapper;

    /**
     * 优惠卷记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CouponRecordDto> List(@RequestBody CouponRecordPagedInput input) {
        return _CouponRecordService.List(input);
    }

    /**
     * 得到可以使用的优惠卷
     */
    @RequestMapping(value = "/GetCanUseCoupon", method = RequestMethod.POST)
    @SneakyThrows
    public List<CouponRecordDto> GetCanUseCoupon(@RequestBody CouponRecordPagedInput input) {
        return _CouponRecordService.GetCanUseCoupon(input);
    }


    /**
     * 单个优惠卷记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public CouponRecordDto Get(@RequestBody CouponRecordPagedInput input) {

        return _CouponRecordService.Get(input);
    }

    /**
     * 优惠卷记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public CouponRecordDto CreateOrEdit(@RequestBody CouponRecordDto input) throws Exception {
        return _CouponRecordService.CreateOrEdit(input);
    }

    /**
     * 优惠卷记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        _CouponRecordService.Delete(input);
    }

    /**
     * 优惠卷记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        _CouponRecordService.BatchDelete(input);
    }


}
