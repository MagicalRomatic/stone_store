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
 * 文章功能的Service接口的定义清单
 */
public interface ArticleService extends IService<Article> {

    /**
     * 文章的分页查询方法接口定义
     */
    public PagedResult<ArticleDto> List(ArticlePagedInput input) ;
    /**
     * 文章的新增或者修改方法接口定义
     */
    public ArticleDto CreateOrEdit(ArticleDto input);

     /**
     * 获取文章信息
     */
    public ArticleDto Get(ArticlePagedInput input);
 	 /**
     * 文章删除
     */
    public void Delete(IdInput input);

    /**
     * 文章批量删除
     */
    public void BatchDelete(IdsInput input);
  
      /**
     * 浏览数量递增
     */
    public void AddReadCount(IdInput input);

}
