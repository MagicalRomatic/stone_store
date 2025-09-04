package com.example.web.controller;
import com.example.web.enums.*;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Select")
public class SelectController {

   @RequestMapping(value = "/RoleType",method = RequestMethod.POST)
    public PagedResult<SelectResult> RoleType(){

        List<SelectResult> rs = Arrays.stream(RoleTypeEnum.values()).map(n -> new SelectResult(n.toString(), n.name(), n.index() + "", "")).toList();
        return PagedResult.GetInstance(rs,rs.stream().count());
    }
     /**
     *店铺审核状态枚举接口
     */
    @RequestMapping(value = "/ShopAuditStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> ShopAuditStatusEnum() {

       var rs=Arrays.stream(ShopAuditStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *订单售后类型枚举接口
     */
    @RequestMapping(value = "/OrderReturnTypeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> OrderReturnTypeEnum() {

       var rs=Arrays.stream(OrderReturnTypeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *订单状态枚举接口
     */
    @RequestMapping(value = "/OrderStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> OrderStatusEnum() {

       var rs=Arrays.stream(OrderStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
      
    
}
