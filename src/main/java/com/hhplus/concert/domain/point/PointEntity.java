package com.hhplus.concert.domain.point;

import com.hhplus.concert.domain.point.PointException.PointError;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "point")
public class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long point;

    @Version
    private long version;

    public void charge(long amount) {
        this.point += amount;
    }

    public void pay(long price) {
        if (point < price) {
            throw new PointException(PointError.INSUFFICIENT_POINT_ERROR);
        }
        this.point -= price;
    }

    public PointEntity(long id, long userId, long point) {
        this.id = id;
        this.userId = userId;
        this.point = point;
        this.version = 1;
    }
}
