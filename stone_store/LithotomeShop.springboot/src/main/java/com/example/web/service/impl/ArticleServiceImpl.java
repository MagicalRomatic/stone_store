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
 * 文章功能实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的Article表mapper对象
     */
    @Autowired
    private ArticleMapper _ArticleMpper;
    @Autowired
    private ArticleTypeMapper _ArticleTypeMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Article> BuilderQuery(ArticlePagedInput input) {
        //声明一个支持文章查询的(拉姆达)表达式
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.<Article>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Article::getId, input.getId())
                .eq(input.getCreatorId() != null, Article::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
            queryWrapper = queryWrapper.like(Article::getTitle, input.getTitle());
        }

        if (input.getArticleTypeId() != null) {
            queryWrapper = queryWrapper.eq(Article::getArticleTypeId, input.getArticleTypeId());
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(Article::getContent, input.getContent());
        }
        if (input.getIsTop() != null) {
            queryWrapper = queryWrapper.eq(Article::getIsTop, input.getIsTop());
        }
        return queryWrapper;
    }
    /**
     * 处理文章对于的外键数据
     */
    private List<ArticleDto> DispatchItem(List<ArticleDto> items) throws InvocationTargetException, IllegalAccessException {

        for (ArticleDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            //查询出关联的ArticleType表信息
            ArticleType ArticleTypeEntity = _ArticleTypeMapper.selectList(Wrappers.<ArticleType>lambdaQuery().eq(ArticleType::getId, item.getArticleTypeId())).stream().findFirst().orElse(new ArticleType());
            item.setArticleTypeDto(ArticleTypeEntity.MapToDto());
        }
        return items;
    }

    /**
     * 文章分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<ArticleDto> List(ArticlePagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<Article> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Article::getCreationTime);
        //构建一个分页查询的model
        Page<Article> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取文章数据
        IPage<Article> pageRecords = _ArticleMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = _ArticleMpper.selectCount(queryWrapper);
        //把Article实体转换成Article传输模型
        List<ArticleDto> items = Extension.copyBeanList(pageRecords.getRecords(), ArticleDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个文章查询
     */
    @SneakyThrows
    @Override
    public ArticleDto Get(ArticlePagedInput input) {
        if (input.getId() == null) {
            return new ArticleDto();
        }
        PagedResult<ArticleDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new ArticleDto());
    }

    /**
     * 文章创建或者修改
     */
    @SneakyThrows
    @Override
    public ArticleDto CreateOrEdit(ArticleDto input) {
        //声明一个文章实体
        Article Article = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Article);
        //把传输模型返回给前端
        return Article.MapToDto();
    }

    /**
     * 文章删除
     */
    @Override
    public void Delete(IdInput input) {
        Article entity = _ArticleMpper.selectById(input.getId());
        _ArticleMpper.deleteById(entity);
    }

    /**
     * 文章批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
    /**
     * 浏览数量递增
     */
    @Override
    public void AddReadCount(IdInput input) {
        Article article = _ArticleMpper.selectById(input.getId());
        article.setReadCount(article.getReadCount() + 1);
        _ArticleMpper.updateById(article);
    }
}
