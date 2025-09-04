package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.GoodTypeDto;
import com.example.web.dto.query.GoodTypePagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.GoodType;
import com.example.web.entity.Shop;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.GoodTypeMapper;
import com.example.web.mapper.ShopMapper;
import com.example.web.service.GoodTypeService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 石材分类功能实现类
 */
@Service
public class GoodTypeServiceImpl extends ServiceImpl<GoodTypeMapper, GoodType> implements GoodTypeService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper  AppUserMapper;
    /**
     * 操作数据库的GoodType表mapper对象
     */
    @Autowired
    private GoodTypeMapper  GoodTypeMapper;

    @Autowired
    private ShopMapper  ShopMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<GoodType> BuilderQuery(GoodTypePagedInput input) {
        //声明一个支持石材分类查询的(拉姆达)表达式
        LambdaQueryWrapper<GoodType> queryWrapper = Wrappers.<GoodType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, GoodType::getId, input.getId())
                .eq(input.getCreatorId() != null, GoodType::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNameLike())) {
            queryWrapper = queryWrapper.like(GoodType::getName, input.getNameLike());
        }
        if (input.getShopId() != null) {
            queryWrapper = queryWrapper.eq(GoodType::getShopId, input.getShopId());
        }
        if (input.getIsShop() != null) {
            queryWrapper = queryWrapper.eq(GoodType::getIsShop, input.getIsShop());
        }

        return queryWrapper;
    }

    /**
     * 处理石材分类对于的外键数据
     */
    private List<GoodTypeDto> DispatchItem(List<GoodTypeDto> items) throws InvocationTargetException, IllegalAccessException {

        for (GoodTypeDto item : items) {
            //查询出关联的创建用户信息

            AppUser CreatorAppUserEntity = AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getCreatorId())).stream().findFirst().orElse(new AppUser());
            item.setCreatorAppUserDto(CreatorAppUserEntity.MapToDto());


            Shop ShopEntity = ShopMapper.selectList(Wrappers.<Shop>lambdaQuery().eq(Shop::getId, item.getShopId())).stream().findFirst().orElse(new Shop());
            item.setShopDto(ShopEntity.MapToDto());



        }
        return items;
    }

    /**
     * 石材分类分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GoodTypeDto> List(GoodTypePagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<GoodType> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(GoodType::getSort);
        //构建一个分页查询的model
        Page<GoodType> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取石材分类数据
        IPage<GoodType> pageRecords = GoodTypeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = GoodTypeMapper.selectCount(queryWrapper);
        //把GoodType实体转换成GoodType传输模型
        List<GoodTypeDto> items = Extension.copyBeanList(pageRecords.getRecords(), GoodTypeDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个石材分类查询
     */
    @SneakyThrows
    @Override
    public GoodTypeDto Get(GoodTypePagedInput input) {
        if (input.getId() == null) {
            return new GoodTypeDto();
        }
        PagedResult<GoodTypeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GoodTypeDto());
    }

    /**
     * 石材分类创建或者修改
     */
    @SneakyThrows
    @Override
    public GoodTypeDto CreateOrEdit(GoodTypeDto input) {
        //声明一个石材分类实体
        GoodType GoodType = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(GoodType);
        //把传输模型返回给前端
        return GoodType.MapToDto();
    }

    /**
     * 石材分类删除
     */
    @Override
    public void Delete(IdInput input) {
        GoodType entity = GoodTypeMapper.selectById(input.getId());
        GoodTypeMapper.deleteById(entity);
    }

    /**
     * 石材分类批量删除
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
     * 石材分类导出
     */
    @Override
    public void Export(@RequestParam String query, HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        GoodTypePagedInput input = mapper.readValue(query, GoodTypePagedInput.class);
        List<GoodTypeDto> items = List(input).getItems();
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //生成一个表格，设置表格名称为"石材分类"
        HSSFSheet sheet = workbook.createSheet("石材分类表");
        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(30);
        //创建标题的显示样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);
        //表头数据
        String[] header = {"名称", "排序",};
        //遍历添加表头(下面模拟遍历石材分类，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);
            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
            cell.setCellStyle(headerStyle);
        }
        for (int i = 0; i < items.size(); i++) {
            GoodTypeDto dto = items.get(i);
            //创建一行
            HSSFRow row = sheet.createRow(i + 1);

            if (Extension.isNotNullOrEmpty(dto.getName())) {
                row.createCell(0).setCellValue(new HSSFRichTextString(dto.getName()));
            }
            if (dto.getSort() != null) {
                row.createCell(1).setCellValue(new HSSFRichTextString(dto.getSort() + ""));
            }
        }
        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");
        //这后面可以设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + System.currentTimeMillis() + ".xls");
        //刷新缓冲
        response.flushBuffer();
        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
    }
}
