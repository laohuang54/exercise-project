package com.fth.controller.FuncController;

import com.fth.dto.CommentsDTO;
import com.fth.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentsController {
    @PostMapping("/add")
    public Result addComments(@RequestBody CommentsDTO commentsDTO) {
        // TODO 实现多级评论功能
        return null;
    }

}
