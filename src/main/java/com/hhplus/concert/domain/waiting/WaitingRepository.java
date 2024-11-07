package com.hhplus.concert.domain.waiting;

import com.hhplus.concert.infra.redis.waiting.WaitingParam;
import java.util.List;

public interface WaitingRepository {

    void issue(WaitingParam.Issue token);

    void activate(List<WaitingParam.Activate> param);

    List<WaitingInfo.ActivateTarget> findActivateTargets(WaitingParam.ActivateTarget search);

    WaitingInfo.TokenInfo findWaitToken(WaitingParam.Search search);

    WaitingInfo.TokenInfo findActiveToken(WaitingParam.Search search);

    void expire(WaitingParam.Search search);
}
