package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.LeaveFeedBackDto;
import com.example.web.dto.query.LeaveFeedBackPagedInput;
import com.example.web.entity.LeaveFeedBack;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 留言反馈功能的Service接口的定义清单
 */
public interface LeaveFeedBackService extends IService<LeaveFeedBack> {

    /**
     * 留言反馈的分页查询方法接口定义
     */
    public PagedResult<LeaveFeedBackDto> List(LeaveFeedBackPagedInput input);

    /**
     * 留言反馈的新增或者修改方法接口定义
     */
    public LeaveFeedBackDto CreateOrEdit(LeaveFeedBackDto input);

    /**
     * 获取留言反馈信息
     */
    public LeaveFeedBackDto Get(LeaveFeedBackPagedInput input);

    /**
     * 留言反馈删除
     */
    public void Delete(IdInput input);

    /**
     * 留言反馈批量删除
     */
    public void BatchDelete(IdsInput input);


}
