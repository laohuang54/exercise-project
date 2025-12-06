package com.fth.mapper;

import com.fth.pojo.Essay;
import com.fth.vo.EssayVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EssayMapper {

    void insert(Essay essay);

    @Delete("delete from essay where id=#{id} and user_id=#{userId}")
    void deleteById(Integer id, Integer userId);

    @Delete("delete from essay where id=#{id}")
    void adminDeleteById(Integer id);

    @Update("update essay set liked=liked+1 where id=#{id}")
    void incryLikes(Integer id);

    @Update("update essay set liked=liked-1 where id=#{id}")
    void decryLikes(Integer id);

    @Select("select e.*,u.username,u.avatar from essay e left join user u on e.user_id=u.id")
    List<EssayVO> getAllessay();

    @Select("select * from essay where id=#{id}")
    Essay getSingleEssay(Integer id);
}
