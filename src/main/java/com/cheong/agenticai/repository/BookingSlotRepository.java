package com.cheong.agenticai.repository;

import com.cheong.agenticai.model.BookingSlot;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BookingSlotRepository extends R2dbcRepository<BookingSlot, String> {

    Mono<Integer> countBookingSlotByParkingSlotIdEqualsAndStatusInAndStartDateTimeIsGreaterThanEqualAndEndDateTimeIsLessThanEqual(
            String parkingSlotId, Collection<BookingSlot.Status> statuses, LocalDateTime startDateTime, LocalDateTime endDateTime);

    Mono<BookingSlot> findByParkingSlotIdEqualsAndUserIdEquals(String parkingSlotId, String userId);

    Mono<BookingSlot> findByParkingSlotIdEquals(String parkingSlotId);
}
