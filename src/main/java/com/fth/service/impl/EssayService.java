package com.fth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.fth.dto.EssayDTO;
import com.fth.dto.Result;
import com.fth.mapper.EssayMapper;
import com.fth.pojo.Essay;
import com.fth.service.IEssayService;
import com.fth.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.fth.constant.KeysConstant.ESSAY_LIKES_KEY;

@Service
public class EssayService implements IEssayService {
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void deleteEssay(Integer id) {
        Integer userId=UserHolder.getUserId();
        essayMapper.deleteById(id,userId);
    }

    @Override
    public void deleteEssayAdmin(Integer id) {
        essayMapper.adminDeleteById(id);
    }

    public Result addEssay(EssayDTO essayDTO, String imgUrl) {
        Essay essay= BeanUtil.copyProperties(essayDTO,Essay.class);
        essay.setUserId(UserHolder.getUserId());
        essay.setImg(imgUrl);
        essayMapper.insert(essay);
        return Result.ok();
    }

    @Override
    @Transactional // 数据库事务：保证点赞数修改和Redis操作一致
    public void likeEssay(Integer id) {
        Integer userId = UserHolder.getUserId();
        String key = ESSAY_LIKES_KEY + id;
        String userStr = userId.toString();

        // 1. 先执行Redis原子操作，根据返回值判断是点赞还是取消点赞（避免并发问题）
        Long isAddSuccess = stringRedisTemplate.opsForSet().add(key, userStr);

        if (isAddSuccess != null) {
            if (Boolean.TRUE.equals(isAddSuccess)) {
                // 2. add成功 → 首次点赞 → 数据库点赞数+1
                essayMapper.incryLikes(id);
            } else {
                // 3. add失败（元素已存在）→ 取消点赞 → 先移除Redis中的用户ID，再数据库点赞数-1
                Long removeCount = stringRedisTemplate.opsForSet().remove(key, userStr);
                if (removeCount != null && removeCount > 0) {
                    essayMapper.decryLikes(id);
                }
            }
        } else {
            // 4. Redis操作失败（如服务不可用）→ 抛出异常或降级处理（根据业务需求）
            throw new RuntimeException("Redis服务异常，点赞操作失败");
        }
    }
}
