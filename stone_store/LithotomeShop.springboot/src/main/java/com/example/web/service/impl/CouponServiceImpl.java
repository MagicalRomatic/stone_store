package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouponDto;
import com.example.web.dto.query.CouponPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Coupon;
import com.example.web.entity.CouponRecord;
import com.example.web.entity.Shop;

import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouponMapper;
import com.example.web.mapper.CouponRecordMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.CouponService;
import com.example.web.tools.BaseContext;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.CurrentUserDto;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

import com.example.web.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠卷功能实现类
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的Coupon表mapper对象
     */
    @Autowired
    private CouponMapper _CouponMpper;

    @Autowired
    private CouponRecordMapper CouponRecordMapper;

    @Autowired
    private ShopMapper _ShopMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Coupon> BuilderQuery(CouponPagedInput input) {
        //声明一个支持优惠卷查询的(拉姆达)表达式
        LambdaQueryWrapper<Coupon> queryWrapper = Wrappers.<Coupon>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Coupon::getId, input.getId())
                .eq(input.getCreatorId() != null, Coupon::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
            queryWrapper = queryWrapper.like(Coupon::getTitle, input.getTitle());
        }
        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(Coupon::getShopId, input.getShopId());
        }
        if (input.getActiveTimeRange() != null && !input.getActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.ge(Coupon::getBeginActiveTime, input.getActiveTimeRange().get(0));
            queryWrapper = queryWrapper.le(Coupon::getEndActiveTime, input.getActiveTimeRange().get(1));
        }
        if (input.getGetCanAppoint() != null && input.getGetCanAppoint() == Boolean.TRUE) {
            CurrentUserDto currentUserDto = BaseContext.getCurrentUserDto();
            List<Integer> couponIds = CouponRecordMapper.selectList(Wrappers.<CouponRecord>lambdaQuery().eq(CouponRecord::getGetUserId, currentUserDto.getUserId())).stream().map(x -> x.getCouponId()).toList();
            queryWrapper.notIn(couponIds.size() > 0, Coupon::getId, couponIds);
            queryWrapper.ge(Coupon::getEndActiveTime, LocalDateTime.now());
            queryWrapper.ne(Coupon::getQty, 0);

        }
        return queryWrapper;
    }

    /**
     * 处理优惠卷对于的外键数据
     */
    private List<CouponDto> DispatchItem(List<CouponDto> items) throws InvocationTargetException, IllegalAccessException {

        for (CouponDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            //查询出关联的Shop表信息
            Shop ShopEntity = _ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId())).stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());

        }
        return items;
    }

    /**
     * 优惠卷分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouponDto> List(CouponPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<Coupon> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Coupon::getCreationTime);
        //构建一个分页查询的model
        Page<Coupon> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取优惠卷数据
        IPage<Coupon> pageRecords = _CouponMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = _CouponMpper.selectCount(queryWrapper);
        //把Coupon实体转换成Coupon传输模型
        List<CouponDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouponDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个优惠卷查询
     */
    @SneakyThrows
    @Override
    public CouponDto Get(CouponPagedInput input) {
        if (input.getId() == null) {
            return new CouponDto();
        }
        PagedResult<CouponDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouponDto());
    }

    /**
     * 优惠卷创建或者修改
     */
    @SneakyThrows
    @Override
    public CouponDto CreateOrEdit(CouponDto input) {

        if (LocalDateTime.now().isAfter(input.getBeginActiveTime())) {
            throw new CustomException("优惠卷有效开始时间不能小于当前时间");
        }


        //声明一个优惠卷实体
        Coupon Coupon = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Coupon);



        //把传输模型返回给前端
        return Coupon.MapToDto();
    }

    /**
     * 优惠卷删除
     */
    @Override
    public void Delete(IdInput input) {
        Coupon entity = _CouponMpper.selectById(input.getId());
        _CouponMpper.deleteById(entity);
    }

    /**
     * 优惠卷批量删除
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
