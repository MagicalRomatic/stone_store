package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.web.dto.LeaveFeedBackDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 留言反馈表
 */
@Data
@TableName("`LeaveFeedBack`")
public class LeaveFeedBack extends BaseEntity {


    /**
     * 反馈标题
     */
    @JsonProperty("Title")
    @TableField(value = "Title", updateStrategy = FieldStrategy.IGNORED)
    private String Title;

    /**
     * 反馈内容
     */
    @JsonProperty("Content")
    @TableField(value = "Content", updateStrategy = FieldStrategy.IGNORED)
    private String Content;

    /**
     * 反馈人
     */
    @JsonProperty("FeedbackUserId")
    @TableField(value = "FeedbackUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer FeedbackUserId;

    /**
     * 是否处理
     */
    @JsonProperty("IsDoWith")
    @TableField(value = "IsDoWith", updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsDoWith;

    /**
     * 处理人
     */
    @JsonProperty("DoWithUserId")
    @TableField(value = "DoWithUserId", updateStrategy = FieldStrategy.IGNORED)
    private Integer DoWithUserId;

    /**
     * 回复内容
     */
    @JsonProperty("ReplayContent")
    @TableField(value = "ReplayContent", updateStrategy = FieldStrategy.IGNORED)
    private String ReplayContent;

    /**
     * 把留言反馈实体转换成留言反馈传输模型
     */
    public LeaveFeedBackDto MapToDto() throws InvocationTargetException, IllegalAccessException {
        LeaveFeedBackDto LeaveFeedBackDto = new LeaveFeedBackDto();
        BeanUtils.copyProperties(LeaveFeedBackDto, this);
        return LeaveFeedBackDto;
    }

}
