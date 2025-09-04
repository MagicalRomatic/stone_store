package com.example.web.controller;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.SneakyThrows;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 文章控制器
 */
@RestController()
@RequestMapping("/Article")
public class ArticleController {
    @Autowired()
    private  ArticleService _ArticleService;
    @Autowired()
    private ArticleMapper _ArticleMapper;
    /**
     * 文章分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ArticleDto> List(@RequestBody ArticlePagedInput input)  {
        return _ArticleService.List(input);
    }
     /**
     * 单个文章查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ArticleDto Get(@RequestBody ArticlePagedInput input) {

        return _ArticleService.Get(input);
    }
  
    /**
     * 文章创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ArticleDto CreateOrEdit(@RequestBody ArticleDto input) throws Exception {
        return _ArticleService.CreateOrEdit(input);
    }
    /**
     * 文章删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        _ArticleService.Delete(input);
    }

    /**
     * 文章批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        _ArticleService.BatchDelete(input);
    }
   /**
     * 浏览数量递增
     */
    @RequestMapping(value = "/AddReadCount", method = RequestMethod.POST)
    public void AddReadCount(@RequestBody IdInput input) {
        _ArticleService.AddReadCount(input);
    }


 
}
