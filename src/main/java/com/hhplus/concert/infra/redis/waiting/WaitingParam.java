package com.hhplus.concert.infra.redis.waiting;

import com.hhplus.concert.domain.waiting.WaitingInfo;
import com.hhplus.concert.domain.waiting.WaitingToken;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WaitingParam {

    public static final String KEY_PREFIX = "token:";
    public static final String WAITING_KEY = KEY_PREFIX + "wait";

    public static String activeKey(String value) {
        return KEY_PREFIX + "active:" + value;
    }

    public record Issue(
        String key,
        String value,
        double score
    ) {

        public static Issue of(WaitingToken token) {
            long score = token.createdAt().atZone(ZoneId.of("Asia/Seoul")).toInstant()
                .toEpochMilli();
            return new Issue(WAITING_KEY, token.token(), score);
        }
    }

    public record ActivateTarget(
        long count
    ) {

    }

    public record Activate(
        String value,
        long expireTime,
        TimeUnit timeUnit
    ) {

        public static List<Activate> of(List<WaitingInfo.ActivateTarget> targets, long expireTime, TimeUnit timeUnit) {
            return targets.stream().map(t -> new Activate(t.token(),expireTime,timeUnit)).toList();
        }

    }

    public record Search(
        String value
    ) {
    }
}
