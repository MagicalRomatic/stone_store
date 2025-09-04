package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 文章类型功能的Service接口的定义清单
 */
public interface ArticleTypeService extends IService<ArticleType> {

    /**
     * 文章类型的分页查询方法接口定义
     */
    public PagedResult<ArticleTypeDto> List(ArticleTypePagedInput input) ;
    /**
     * 文章类型的新增或者修改方法接口定义
     */
    public ArticleTypeDto CreateOrEdit(ArticleTypeDto input);

     /**
     * 获取文章类型信息
     */
    public ArticleTypeDto Get(ArticleTypePagedInput input);
 	 /**
     * 文章类型删除
     */
    public void Delete(IdInput input);

    /**
     * 文章类型批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
