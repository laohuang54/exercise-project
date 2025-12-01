package com.fth.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Fallback;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Essay {
    private Integer id;

    private Integer userId; //外键约束 user表

    private String content;

    private Integer status;

    private Integer liked; // 点赞量

    private Integer comments; // 评论量

    private String img;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime; //发布时间

    private Integer read; // 阅读量

    private String title;
}
