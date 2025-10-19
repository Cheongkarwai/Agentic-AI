package com.cheong.agenticai.repository;

import com.cheong.agenticai.model.BookingSlot;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface BookingSlotRepository extends R2dbcRepository<BookingSlot, String> {

    Mono<Integer> countBookingSlotByParkingSlotIdEqualsAndStatusInAndStartDateTimeIsLessThanAndEndDateTimeIsGreaterThan(
            String parkingSlotId, Collection<BookingSlot.Status> statuses, LocalDateTime endDateTime, LocalDateTime startDateTime);

    Mono<BookingSlot> findFirstByParkingSlotIdEqualsAndStatusInAndTransactionIdIsNull(String id, List<BookingSlot.Status> statuses);
}
