package com.hhplus.concert.domain.user;

import com.hhplus.concert.domain.user.UserException.UserError;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserInfo.Point chargePoint(long userId, long amount) {
        UserEntity user = userRepository.findOneUser(userId).orElseThrow(
            () -> new UserException(UserError.USER_NOT_FOUND));

        user.charge(amount);

        PointHistoryEntity history = new PointHistoryEntity(0, user.getId(), amount,
            PointType.CHARGE, LocalDateTime.now());
        userRepository.insertPointHistory(history);

        return UserInfo.Point.of(user);
    }

    public UserInfo.Point getPoint(long userId) {
        UserEntity user = userRepository.findOneUser(userId).orElseThrow(
            () -> new UserException(UserError.USER_NOT_FOUND));
        return UserInfo.Point.of(user);
    }

}
