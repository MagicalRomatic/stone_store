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
 * 系统通知功能的Service接口的定义清单
 */
public interface SysNoticeService extends IService<SysNotice> {

    /**
     * 系统通知的分页查询方法接口定义
     */
    public PagedResult<SysNoticeDto> List(SysNoticePagedInput input) ;
    /**
     * 系统通知的新增或者修改方法接口定义
     */
    public SysNoticeDto CreateOrEdit(SysNoticeDto input);

     /**
     * 获取系统通知信息
     */
    public SysNoticeDto Get(SysNoticePagedInput input);
 	 /**
     * 系统通知删除
     */
    public void Delete(IdInput input);

    /**
     * 系统通知批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
