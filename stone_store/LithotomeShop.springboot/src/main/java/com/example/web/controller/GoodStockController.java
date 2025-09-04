package com.example.web.controller;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.SneakyThrows;

/**
 * 库存记录控制器
 */
@RestController()
@RequestMapping("/GoodStock")
public class GoodStockController {
    @Autowired()
    private  GoodStockService GoodStockService;
    @Autowired()
    private GoodStockMapper GoodStockMapper;
    /**
     * 库存记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<GoodStockDto> List(@RequestBody GoodStockPagedInput input)  {
        return GoodStockService.List(input);
    }
     /**
     * 单个库存记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public GoodStockDto Get(@RequestBody GoodStockPagedInput input) {

        return GoodStockService.Get(input);
    }
  
    /**
     * 库存记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public GoodStockDto CreateOrEdit(@RequestBody GoodStockDto input) throws Exception {
        return GoodStockService.CreateOrEdit(input);
    }
    /**
     * 库存记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        GoodStockService.Delete(input);
    }

    /**
     * 库存记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        GoodStockService.BatchDelete(input);
    }
  

 
}
