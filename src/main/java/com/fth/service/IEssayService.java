package com.fth.service;

import com.fth.dto.EssayDTO;
import com.fth.dto.Result;
import com.fth.pojo.Essay;

public interface IEssayService {
    Result addEssay(EssayDTO essay, String imgUrl);

    void deleteEssay(Integer id);

    void deleteEssayAdmin(Integer id);

    public Result likeEssay(Integer id);

    Result getAllEssay();

    Essay getSingleEssay(Integer id);
}
