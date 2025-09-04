package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

/**
 * LeaveFeedBack查询模型
 */
@NoArgsConstructor
@Data
public class LeaveFeedBackPagedInput extends PagedInput {

    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 反馈标题模糊查询条件
     */
    @JsonProperty("Title")
    private String Title;
    /**
     * 反馈人
     */
    @JsonProperty("FeedbackUserId")
    private Integer FeedbackUserId;
    /**
     * 处理人
     */
    @JsonProperty("DoWithUserId")
    private Integer DoWithUserId;
    /**
     * 反馈内容模糊查询条件
     */
    @JsonProperty("Content")
    private String Content;
    /**
     * 是否处理
     */
    @JsonProperty("IsDoWith")
    private Boolean IsDoWith;
    /**
     * 回复内容模糊查询条件
     */
    @JsonProperty("ReplayContent")
    private String ReplayContent;

}
