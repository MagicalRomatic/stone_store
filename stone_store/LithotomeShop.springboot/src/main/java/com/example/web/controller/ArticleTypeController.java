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
 * 文章类型控制器
 */
@RestController()
@RequestMapping("/ArticleType")
public class ArticleTypeController {
    @Autowired()
    private  ArticleTypeService _ArticleTypeService;
    @Autowired()
    private ArticleTypeMapper _ArticleTypeMapper;
    /**
     * 文章类型分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<ArticleTypeDto> List(@RequestBody ArticleTypePagedInput input)  {
        return _ArticleTypeService.List(input);
    }
     /**
     * 单个文章类型查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public ArticleTypeDto Get(@RequestBody ArticleTypePagedInput input) {

        return _ArticleTypeService.Get(input);
    }
  
    /**
     * 文章类型创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public ArticleTypeDto CreateOrEdit(@RequestBody ArticleTypeDto input) throws Exception {
        return _ArticleTypeService.CreateOrEdit(input);
    }
    /**
     * 文章类型删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        _ArticleTypeService.Delete(input);
    }

    /**
     * 文章类型批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        _ArticleTypeService.BatchDelete(input);
    }
  

 
}
