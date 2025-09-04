package com.example.web.controller;

import com.example.web.dto.LeaveFeedBackDto;
import com.example.web.dto.query.LeaveFeedBackPagedInput;
import com.example.web.mapper.LeaveFeedBackMapper;
import com.example.web.service.LeaveFeedBackService;
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
 * 留言反馈控制器
 */
@RestController()
@RequestMapping("/LeaveFeedBack")
public class LeaveFeedBackController {
    @Autowired()
    private  LeaveFeedBackService _LeaveFeedBackService;
    @Autowired()
    private LeaveFeedBackMapper _LeaveFeedBackMapper;
    /**
     * 留言反馈分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<LeaveFeedBackDto> List(@RequestBody LeaveFeedBackPagedInput input)  {
        return _LeaveFeedBackService.List(input);
    }
     /**
     * 单个留言反馈查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public LeaveFeedBackDto Get(@RequestBody LeaveFeedBackPagedInput input) {

        return _LeaveFeedBackService.Get(input);
    }
  
    /**
     * 留言反馈创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public LeaveFeedBackDto CreateOrEdit(@RequestBody LeaveFeedBackDto input) throws Exception {
        return _LeaveFeedBackService.CreateOrEdit(input);
    }
    /**
     * 留言反馈删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        _LeaveFeedBackService.Delete(input);
    }

    /**
     * 留言反馈批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        _LeaveFeedBackService.BatchDelete(input);
    }
  

 
}
