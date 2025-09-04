package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.GoodDto;
import com.example.web.dto.query.GoodPagedInput;
import com.example.web.entity.Good;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
/**
 * 石材功能的Service接口的定义清单
 */
public interface GoodService extends IService<Good> {

    /**
     * 协同过滤推荐
     */
    @SneakyThrows
    PagedResult<GoodDto> GetRecommendList(GoodPagedInput input);

    /**
     * 石材的分页查询方法接口定义
     */
    public PagedResult<GoodDto> List(GoodPagedInput input) ;
    /**
     * 石材的新增或者修改方法接口定义
     */
    public GoodDto CreateOrEdit(GoodDto input);

     /**
     * 获取石材信息
     */
    public GoodDto Get(GoodPagedInput input);
 	 /**
     * 石材删除
     */
    public void Delete(IdInput input);

    /**
     * 石材批量删除
     */
    public void BatchDelete(IdsInput input);
  
	  /**
     * 石材导出
     */
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException;

}
