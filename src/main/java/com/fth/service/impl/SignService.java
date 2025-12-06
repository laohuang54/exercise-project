package com.fth.service.impl;

import com.fth.dto.Result;
import com.fth.service.ISignService;
import com.fth.utils.UserHolder;
import com.fth.vo.SignVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fth.constant.KeysConstant.SIGN_KEY;

@Service
@Slf4j
public class SignService implements ISignService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sign() {
        log.info("用户签到");
        Integer userId = UserHolder.getUserId();
        if (userId == null) {
            return Result.fail("用户未登录");
        }

        // 1. 构建当前用户当月的 Bitmap key（格式：sign:123:202512）
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String signKey = SIGN_KEY + userId + ":" + yearMonth;

        log.info("signKey:{}", signKey);

        // 2. 获取当前日期（1~31），转换为 Bitmap 的 offset（0~30）
        int dayOfMonth = now.getDayOfMonth();
        int offset = dayOfMonth - 1; // 关键：日期减 1 对齐 offset

        // 3. 判断是否已签到（查询 Bitmap 对应 offset 的位是否为 1）
        Boolean isSigned = stringRedisTemplate.opsForValue().getBit(signKey, offset);
        if (Boolean.TRUE.equals(isSigned)) {
            return Result.fail("今日已签到，无需重复签到");
        }

        // 4. 签到：将对应 offset 的位设为 1
        stringRedisTemplate.opsForValue().setBit(signKey, offset, true);

        // 5. 可选：统计当月已签到天数（比如返回给前端）
        Long signedCount = getSignedCount(signKey);

        Integer continueSignedCount = getContinueSignCount(signKey, dayOfMonth);
        SignVO signVO = new SignVO();
        signVO.setId(userId);
        signVO.setSignedCount(signedCount);
        signVO.setContinueSignCount(continueSignedCount);
        return Result.ok(signVO);
    }

    private Long getSignedCount(String key) { // 当月已签到天数
        Long execute = stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            // 调用 Redis 的 BITCOUNT 命令，统计 key 中 1 的个数（已签到天数）
            return connection.bitCount(key.getBytes(StandardCharsets.UTF_8));
        });
        return execute;
    }

    private Integer getContinueSignCount(String key,int dayOfMonth) { // 连续签到天数
        Integer count=0;
        int offset=dayOfMonth-1;
        for(int i=offset;i>=0;i--){
            Boolean isSigned = stringRedisTemplate.opsForValue().getBit(key, i);
            if (Boolean.TRUE.equals(isSigned)) {
                count++;
            }
            else{
                break;
            }
        }
        return count;
    }
}
