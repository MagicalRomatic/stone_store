package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodPropDto;
import com.example.web.dto.query.GoodPropPagedInput;
import com.example.web.entity.GoodProp;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 石材属性功能的Service接口的定义清单
 */
public interface GoodPropService extends IService<GoodProp> {

    /**
     * 石材属性的分页查询方法接口定义
     */
    public PagedResult<GoodPropDto> List(GoodPropPagedInput input) ;
    /**
     * 石材属性的新增或者修改方法接口定义
     */
    public GoodPropDto CreateOrEdit(GoodPropDto input);

     /**
     * 获取石材属性信息
     */
    public GoodPropDto Get(GoodPropPagedInput input);
 	 /**
     * 石材属性删除
     */
    public void Delete(IdInput input);

    /**
     * 石材属性批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
