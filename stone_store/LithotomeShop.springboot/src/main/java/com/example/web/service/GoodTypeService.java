package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodTypeDto;
import com.example.web.dto.query.GoodTypePagedInput;
import com.example.web.entity.GoodType;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
/**
 * 石材分类功能的Service接口的定义清单
 */
public interface GoodTypeService extends IService<GoodType> {

    /**
     * 石材分类的分页查询方法接口定义
     */
    public PagedResult<GoodTypeDto> List(GoodTypePagedInput input) ;
    /**
     * 石材分类的新增或者修改方法接口定义
     */
    public GoodTypeDto CreateOrEdit(GoodTypeDto input);

     /**
     * 获取石材分类信息
     */
    public GoodTypeDto Get(GoodTypePagedInput input);
 	 /**
     * 石材分类删除
     */
    public void Delete(IdInput input);

    /**
     * 石材分类批量删除
     */
    public void BatchDelete(IdsInput input);
  
	  /**
     * 石材分类导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

}
