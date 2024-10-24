package com.hhplus.concert.domain.point;

import com.hhplus.concert.domain.point.PointException.PointError;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    @Transactional
    public PointInfo.Common chargePoint(PointCommand.Charge command) {
        PointEntity point = pointRepository.findUserPoint(command.userId()).orElseThrow(
            () -> new PointException(PointError.USER_POINT_NOT_FOUND));

        point.charge(command.amount());

        PointHistoryEntity history = new PointHistoryEntity(0, point.getId(), command.amount(),
            PointType.CHARGE, LocalDateTime.now());
        pointRepository.insertPointHistory(history);

        return PointInfo.Common.of(point);
    }

    public PointInfo.Common getPoint(PointCommand.Get command) {
        PointEntity user = pointRepository.findUserPoint(command.userId()).orElseThrow(
            () -> new PointException(PointError.USER_POINT_NOT_FOUND));
        return PointInfo.Common.of(user);
    }

}
