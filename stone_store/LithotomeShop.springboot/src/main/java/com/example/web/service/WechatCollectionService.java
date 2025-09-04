package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 会话集合功能的Service接口的定义清单
 */
public interface WechatCollectionService extends IService<WechatCollection> {

    /**
     * 会话集合的分页查询方法接口定义
     */
    public PagedResult<WechatCollectionDto> List(WechatCollectionPagedInput input) ;
    /**
     * 会话集合的新增或者修改方法接口定义
     */
    public WechatCollectionDto CreateOrEdit(WechatCollectionDto input);

     /**
     * 获取会话集合信息
     */
    public WechatCollectionDto Get(WechatCollectionPagedInput input);
    /**
     * 同步聊天记录到列表
     */
    @SneakyThrows
    void SyncWechatCollection(Integer selfUserId, Integer otherUserId, String message);

    /**
     * 添加会话到双方列表
     */
    @SneakyThrows
    void InsertWechatCollection(WechatCollectionDto input);

    /**
     * 会话集合删除
     */
    public void Delete(IdInput input);

    /**
     * 会话集合批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
