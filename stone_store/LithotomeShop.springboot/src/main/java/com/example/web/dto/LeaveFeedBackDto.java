package com.example.web.dto;

import com.example.web.entity.LeaveFeedBack;
import com.example.web.tools.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * 留言反馈类
 */
@Data
public class LeaveFeedBackDto extends BaseDto {


    /**
     * 反馈标题
     */
    @JsonProperty("Title")
    private String Title;


    /**
     * 反馈内容
     */
    @JsonProperty("Content")
    private String Content;


    /**
     * 反馈人
     */
    @JsonProperty("FeedbackUserId")
    private Integer FeedbackUserId;


    /**
     * 是否处理
     */
    @JsonProperty("IsDoWith")
    private Boolean IsDoWith;


    /**
     * 处理人
     */
    @JsonProperty("DoWithUserId")
    private Integer DoWithUserId;


    /**
     * 回复内容
     */
    @JsonProperty("ReplayContent")
    private String ReplayContent;

    @JsonProperty("FeedbackUserDto")
    private AppUserDto FeedbackUserDto;

    @JsonProperty("DoWithUserDto")
    private AppUserDto DoWithUserDto;

    /**
     * 把留言反馈传输模型转换成留言反馈实体
     */
    public LeaveFeedBack MapToEntity() throws InvocationTargetException, IllegalAccessException {
        LeaveFeedBack LeaveFeedBack = new LeaveFeedBack();
        BeanUtils.copyProperties(LeaveFeedBack, this);
        return LeaveFeedBack;
    }

}
