package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.LeaveFeedBackDto;
import com.example.web.dto.query.LeaveFeedBackPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.LeaveFeedBack;

import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.LeaveFeedBackMapper;
import com.example.web.service.LeaveFeedBackService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 留言反馈功能实现类
 */
@Service
public class LeaveFeedBackServiceImpl extends ServiceImpl<LeaveFeedBackMapper, LeaveFeedBack> implements LeaveFeedBackService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的LeaveFeedBack表mapper对象
     */
    @Autowired
    private LeaveFeedBackMapper _LeaveFeedBackMpper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<LeaveFeedBack> BuilderQuery(LeaveFeedBackPagedInput input) {
        //声明一个支持留言反馈查询的(拉姆达)表达式
        LambdaQueryWrapper<LeaveFeedBack> queryWrapper = Wrappers.<LeaveFeedBack>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, LeaveFeedBack::getId, input.getId())
                .eq(input.getCreatorId() != null, LeaveFeedBack::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
            queryWrapper = queryWrapper.like(LeaveFeedBack::getTitle, input.getTitle());
        }

        if (input.getFeedbackUserId() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedBack::getFeedbackUserId, input.getFeedbackUserId());
        }

        if (input.getDoWithUserId() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedBack::getDoWithUserId, input.getDoWithUserId());
        }
        if (Extension.isNotNullOrEmpty(input.getContent())) {
            queryWrapper = queryWrapper.like(LeaveFeedBack::getContent, input.getContent());
        }
        if (input.getIsDoWith() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedBack::getIsDoWith, input.getIsDoWith());
        }
        if (Extension.isNotNullOrEmpty(input.getReplayContent())) {
            queryWrapper = queryWrapper.like(LeaveFeedBack::getReplayContent, input.getReplayContent());
        }
        return queryWrapper;
    }

    /**
     * 处理留言反馈对于的外键数据
     */
    private List<LeaveFeedBackDto> DispatchItem(List<LeaveFeedBackDto> items) throws InvocationTargetException, IllegalAccessException {

        for (LeaveFeedBackDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            //查询出关联的AppUser表信息
            AppUser FeedbackUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getFeedbackUserId())).stream().findFirst().orElse(new AppUser());
            item.setFeedbackUserDto(FeedbackUserEntity.MapToDto());


            //查询出关联的AppUser表信息
            AppUser DoWithUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getDoWithUserId())).stream().findFirst().orElse(new AppUser());
            item.setDoWithUserDto(DoWithUserEntity.MapToDto());


        }
        return items;
    }

    /**
     * 留言反馈分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<LeaveFeedBackDto> List(LeaveFeedBackPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<LeaveFeedBack> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(LeaveFeedBack::getCreationTime);
        //构建一个分页查询的model
        Page<LeaveFeedBack> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取留言反馈数据
        IPage<LeaveFeedBack> pageRecords = _LeaveFeedBackMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = _LeaveFeedBackMpper.selectCount(queryWrapper);
        //把LeaveFeedBack实体转换成LeaveFeedBack传输模型
        List<LeaveFeedBackDto> items = Extension.copyBeanList(pageRecords.getRecords(), LeaveFeedBackDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个留言反馈查询
     */
    @SneakyThrows
    @Override
    public LeaveFeedBackDto Get(LeaveFeedBackPagedInput input) {
        if (input.getId() == null) {
            return new LeaveFeedBackDto();
        }
        PagedResult<LeaveFeedBackDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new LeaveFeedBackDto());
    }

    /**
     * 留言反馈创建或者修改
     */
    @SneakyThrows
    @Override
    public LeaveFeedBackDto CreateOrEdit(LeaveFeedBackDto input) {
        //声明一个留言反馈实体
        LeaveFeedBack LeaveFeedBack = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(LeaveFeedBack);
        //把传输模型返回给前端
        return LeaveFeedBack.MapToDto();
    }

    /**
     * 留言反馈删除
     */
    @Override
    public void Delete(IdInput input) {
        LeaveFeedBack entity = _LeaveFeedBackMpper.selectById(input.getId());
        _LeaveFeedBackMpper.deleteById(entity);
    }

    /**
     * 留言反馈批量删除
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
