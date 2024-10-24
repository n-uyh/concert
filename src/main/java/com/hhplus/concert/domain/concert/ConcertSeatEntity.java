package com.hhplus.concert.domain.concert;

import com.hhplus.concert.domain.concert.ConcertException.ConcertError;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "concert_seat")
public class ConcertSeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long concertId;

    private int seatNo;
    private long price;
    private boolean occupied;

    public void occupy() {
        this.occupied = true;
    }

    public void checkOccupied() {
        if (occupied) {
            throw new ConcertException(ConcertError.SEAT_ALREADY_OCCUPIED);
        }
    }

    public void releaseOccupancy() {
        this.occupied = false;
    }
}
