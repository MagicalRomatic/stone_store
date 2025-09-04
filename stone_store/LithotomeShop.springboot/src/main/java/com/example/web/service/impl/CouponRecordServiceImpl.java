package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.CouponRecordDto;
import com.example.web.dto.query.CouponRecordPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.Coupon;
import com.example.web.entity.CouponRecord;
import com.example.web.entity.Shop;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.CouponMapper;
import com.example.web.mapper.CouponRecordMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.CouponRecordService;
import com.example.web.tools.Extension;
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
 * 优惠卷记录功能实现类
 */
@Service
public class CouponRecordServiceImpl extends ServiceImpl<CouponRecordMapper, CouponRecord> implements CouponRecordService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的CouponRecord表mapper对象
     */
    @Autowired
    private CouponRecordMapper CouponRecordMapper;
    @Autowired
    private CouponMapper _CouponMapper;
    @Autowired
    private ShopMapper _ShopMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CouponRecord> BuilderQuery(CouponRecordPagedInput input) {
        //声明一个支持优惠卷记录查询的(拉姆达)表达式
        LambdaQueryWrapper<CouponRecord> queryWrapper = Wrappers.<CouponRecord>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CouponRecord::getId, input.getId())
                .eq(input.getCreatorId() != null, CouponRecord::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getRelativeNo())) {
            queryWrapper = queryWrapper.like(CouponRecord::getRelativeNo, input.getRelativeNo());
        }

        if (input.getCouponId() != null) {
            queryWrapper = queryWrapper.eq(CouponRecord::getCouponId, input.getCouponId());
        }
        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(CouponRecord::getShopId, input.getShopId());
        }

        if (input.getGetUserId() != null) {
            queryWrapper = queryWrapper.eq(CouponRecord::getGetUserId, input.getGetUserId());
        }
        if (input.getUseTimeRange() != null && !input.getUseTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(CouponRecord::getUseTime, input.getUseTimeRange().get(1));
            queryWrapper = queryWrapper.gt(CouponRecord::getUseTime, input.getUseTimeRange().get(0));
        }
        if (input.getActiveTimeRange() != null && !input.getActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.ge(CouponRecord::getBeginActiveTime, input.getActiveTimeRange().get(0));
            queryWrapper = queryWrapper.le(CouponRecord::getEndActiveTime, input.getActiveTimeRange().get(1));
        }
        return queryWrapper;
    }

    /**
     * 处理优惠卷记录对于的外键数据
     */
    private List<CouponRecordDto> DispatchItem(List<CouponRecordDto> items) throws InvocationTargetException, IllegalAccessException {

        for (CouponRecordDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());

            //查询出关联的Coupon表信息
            Coupon CouponEntity = _CouponMapper.selectList(Wrappers.<Coupon>lambdaQuery().eq(Coupon::getId, item.getCouponId())).stream().findFirst().orElse(new Coupon());
            item.setCouponDto(CouponEntity.MapToDto());

            //查询出关联的Shop表信息
            Shop ShopEntity = _ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId())).stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());


            //查询出关联的AppUser表信息
            AppUser GetUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getGetUserId())).stream().findFirst().orElse(new AppUser());
            item.setGetUserDto(GetUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 得到可以使用的优惠卷
     */
    @Override
    public List<CouponRecordDto> GetCanUseCoupon(CouponRecordPagedInput input) throws InvocationTargetException, IllegalAccessException {
        LambdaQueryWrapper<CouponRecord> queryWrapper = Wrappers.<CouponRecord>lambdaQuery()
                .eq(CouponRecord::getGetUserId, input.getGetUserId()).le(CouponRecord::getBeginActiveTime, LocalDateTime.now()).ge(CouponRecord::getEndActiveTime, LocalDateTime.now())
                .in(CouponRecord::getShopId,input.getShopIds())
                .isNull(CouponRecord::getUseTime);
        List<CouponRecord> couponRecordList = CouponRecordMapper.selectList(queryWrapper);
        //把CouponRecord实体转换成CouponRecord传输模型
        List<CouponRecordDto> items = Extension.copyBeanList(couponRecordList, CouponRecordDto.class);
        //计算表的数据
        items = DispatchItem(items);
        items = items.stream().filter(x -> x.getCouponDto() != null).toList();
        return items;
    }

    /**
     * 优惠卷记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CouponRecordDto> List(CouponRecordPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<CouponRecord> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(CouponRecord::getCreationTime);
        //构建一个分页查询的model
        Page<CouponRecord> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取优惠卷记录数据
        IPage<CouponRecord> pageRecords = CouponRecordMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = CouponRecordMapper.selectCount(queryWrapper);
        //把CouponRecord实体转换成CouponRecord传输模型
        List<CouponRecordDto> items = Extension.copyBeanList(pageRecords.getRecords(), CouponRecordDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个优惠卷记录查询
     */
    @SneakyThrows
    @Override
    public CouponRecordDto Get(CouponRecordPagedInput input) {
        if (input.getId() == null) {
            return new CouponRecordDto();
        }
        PagedResult<CouponRecordDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new CouponRecordDto());
    }

    /**
     * 优惠卷记录创建或者修改
     */
    @SneakyThrows
    @Override
    public CouponRecordDto CreateOrEdit(CouponRecordDto input) {

        if (Extension.isNullOrZero(input.getId())) {
            Coupon coupon = _CouponMapper.selectById(input.getCouponId());
            if (coupon.getQty() == 0) {
                throw new CustomException("该优惠卷已经被抢光了");
            }
            coupon.setQty(coupon.getQty() - 1);
            coupon.setGetQty(coupon.getGetQty() + 1);
            _CouponMapper.updateById(coupon);
        }

        //声明一个优惠卷记录实体
        CouponRecord CouponRecord = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(CouponRecord);
        //把传输模型返回给前端
        return CouponRecord.MapToDto();
    }

    /**
     * 优惠卷记录删除
     */
    @Override
    public void Delete(IdInput input) {
        CouponRecord entity = CouponRecordMapper.selectById(input.getId());
        CouponRecordMapper.deleteById(entity);
    }

    /**
     * 优惠卷记录批量删除
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
