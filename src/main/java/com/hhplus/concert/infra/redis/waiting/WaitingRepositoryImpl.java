package com.hhplus.concert.infra.redis.waiting;

import com.hhplus.concert.domain.waiting.WaitingInfo;
import com.hhplus.concert.domain.waiting.WaitingInfo.ActivateTarget;
import com.hhplus.concert.domain.waiting.WaitingRepository;
import com.hhplus.concert.infra.redis.waiting.WaitingParam.Activate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void issue(WaitingParam.Issue param) {
        redisTemplate.opsForZSet()
            .add(WaitingParam.WAITING_KEY, param.value(), param.score());
    }

    @Override
    public WaitingInfo.TokenInfo findWaitToken(WaitingParam.Search search) {
        ZSetOperations<String, String> zSet = redisTemplate.opsForZSet();
        Long rank = zSet.rank(WaitingParam.WAITING_KEY, search.value());
        return WaitingInfo.TokenInfo.waiting(search.value(), rank);
    }

    @Override
    public List<WaitingInfo.ActivateTarget> findActivateTargets(WaitingParam.ActivateTarget search) {
        Set<TypedTuple<String>> targets = redisTemplate.opsForZSet()
            .popMin(WaitingParam.WAITING_KEY, search.count());

        if (targets == null || targets.isEmpty()) {
            return List.of();
        }

        return targets.stream().map(TypedTuple::getValue)
            .map(ActivateTarget::new).toList();
    }

    @Override
    public void activate(List<WaitingParam.Activate> targets) {
        for (Activate target : targets) {
            redisTemplate.opsForValue()
                .set(WaitingParam.activeKey(target.value()), target.value(), target.expireTime(), target.timeUnit());
        }
    }

    @Override
    public WaitingInfo.TokenInfo findActiveToken(WaitingParam.Search search) {
        String token = redisTemplate.opsForValue().get(WaitingParam.activeKey(search.value()));
        return WaitingInfo.TokenInfo.acitve(token);
    }

    @Override
    public void expire(WaitingParam.Search search) {
        redisTemplate.opsForValue().getAndDelete(WaitingParam.activeKey(search.value()));
    }
}
