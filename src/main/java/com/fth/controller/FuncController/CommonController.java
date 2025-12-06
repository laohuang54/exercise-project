package com.fth.controller.FuncController;

import com.fth.dto.CommentsDTO;
import com.fth.dto.Result;
import com.fth.service.impl.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private SignService signService;
    // 发表评论
    @PostMapping("/comments/add")
    public Result addComments(@RequestBody CommentsDTO commentsDTO) {
        // TODO 实现多级评论功能
        return null;
    }

    @PutMapping("/signin")
    public Result signIn() {
        return signService.sign();
    }
}
