package com.cheong.agenticai.repository;

import com.cheong.agenticai.model.ParkingSlot;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ParkingSlotRepository extends R2dbcRepository<ParkingSlot, String> {
    Mono<ParkingSlot> findBySlotNoEquals(String slotNo);
}
