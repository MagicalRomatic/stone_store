package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表对应的Mapper
 */
@Mapper
public interface ArticleMapper  extends BaseMapper<Article> {

}
