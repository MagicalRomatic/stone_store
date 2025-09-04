package com.example.web.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import java.io.IOException;
import com.example.web.tools.*;
import java.text.DecimalFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 文章类型功能实现类
 */
@Service
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements ArticleTypeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的ArticleType表mapper对象
     */
    @Autowired
    private ArticleTypeMapper _ArticleTypeMpper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<ArticleType> BuilderQuery(ArticleTypePagedInput input) {
       //声明一个支持文章类型查询的(拉姆达)表达式
        LambdaQueryWrapper<ArticleType> queryWrapper = Wrappers.<ArticleType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, ArticleType::getId, input.getId())
                .eq(input.getCreatorId() != null, ArticleType::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(ArticleType::getName, input.getName());
       	 }
      
      return queryWrapper;
    }
  
    /**
     * 处理文章类型对于的外键数据
     */
   private List<ArticleTypeDto> DispatchItem(List<ArticleTypeDto> items) throws InvocationTargetException, IllegalAccessException {
   
    for (ArticleTypeDto item : items) {        
          //查询出关联的创建用户信息
           
            AppUser  CreatorAppUserEntity= _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId,item.getCreatorId())).stream().findFirst().orElse(new AppUser());
  				 item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());        }
     return items; 
   }
  
    /**
     * 文章类型分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ArticleTypeDto> List(ArticleTypePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<ArticleType> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
         queryWrapper=queryWrapper.orderByDesc(ArticleType::getSort);
        //构建一个分页查询的model
        Page<ArticleType> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取文章类型数据
        IPage<ArticleType> pageRecords= _ArticleTypeMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= _ArticleTypeMpper.selectCount(queryWrapper);
        //把ArticleType实体转换成ArticleType传输模型
        List<ArticleTypeDto> items= Extension.copyBeanList(pageRecords.getRecords(),ArticleTypeDto.class);
        //计算表的数据
        items = DispatchItem(items);

      
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个文章类型查询
     */
    @SneakyThrows
    @Override
    public ArticleTypeDto Get(ArticleTypePagedInput input) {
       if(input.getId()==null)
        {
            return new ArticleTypeDto();
        }
        PagedResult<ArticleTypeDto>  pagedResult =List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ArticleTypeDto());   
    }

    /**
     *文章类型创建或者修改
     */
    @SneakyThrows
    @Override
    public ArticleTypeDto CreateOrEdit(ArticleTypeDto input) {
        //声明一个文章类型实体
        ArticleType ArticleType=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(ArticleType);
        //把传输模型返回给前端
        return ArticleType.MapToDto();
    }
    /**
     * 文章类型删除
     */
    @Override
    public void Delete(IdInput input) {
        ArticleType entity = _ArticleTypeMpper.selectById(input.getId());
        _ArticleTypeMpper.deleteById(entity);
    }

    /**
     * 文章类型批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
}
