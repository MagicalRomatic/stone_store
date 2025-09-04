package com.example.web.dto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.example.web.entity.OrderComment;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 订单评价类
 */
@Data
public class OrderCommentDto extends BaseDto {

    /**
     * 石材
     */
    @JsonProperty("GoodId")
    private Integer GoodId;

    /**
     * 订单
     */
    @JsonProperty("OrderId")
    private Integer OrderId;

    /**
     * 评分
     */
    @JsonProperty("CommentScore")
    private Double CommentScore;

    /**
     * 评论
     */
    @JsonProperty("Comment")
    private String Comment;

    /**
     * 店铺
     */
    @JsonProperty("ShopId")
    private Integer ShopId;

    /**
     * 图片
     */
    @JsonProperty("ImageUrls")
    private String ImageUrls;

    /**
     * 评论人
     */
    @JsonProperty("CommentUserId")
    private Integer CommentUserId;

    @JsonProperty("ShopDto")
    private ShopDto ShopDto;

    @JsonProperty("CommentUserDto")
    private AppUserDto CommentUserDto;

    @JsonProperty("OrderDto")
    private OrderInfoDto OrderDto;

    @JsonProperty("GoodDto")
    private GoodDto GoodDto;

    /**
     * 回复图片
     */
    @JsonProperty("ReplyImageUrls")
    private String ReplyImageUrls;

    /**
     * 回复内容
     */
    @JsonProperty("ReplyContent")
    private String ReplyContent;

    /**
     * 把订单评价传输模型转换成订单评价实体
     */
    public OrderComment MapToEntity() throws InvocationTargetException, IllegalAccessException {
        OrderComment OrderComment = new OrderComment();
        BeanUtils.copyProperties(OrderComment, this);
        return OrderComment;
    }

}
