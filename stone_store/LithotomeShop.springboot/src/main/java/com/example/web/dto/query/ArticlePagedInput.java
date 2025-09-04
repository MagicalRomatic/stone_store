package com.example.web.dto.query;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

/**
 * Article查询模型
 */
@NoArgsConstructor
@Data
public class ArticlePagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 标题模糊查询条件
     */
    @JsonProperty("Title")
    private String Title;
    /**
     * 文章分类
     */
    @JsonProperty("ArticleTypeId")
    private Integer ArticleTypeId;
    /**
     * 内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;
    /**
     * 是否置顶
     */
    @JsonProperty("IsTop")
    private Boolean IsTop;
}
