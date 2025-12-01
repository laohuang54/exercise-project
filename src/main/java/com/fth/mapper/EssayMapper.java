package com.fth.mapper;

import com.fth.pojo.Essay;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

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
}
