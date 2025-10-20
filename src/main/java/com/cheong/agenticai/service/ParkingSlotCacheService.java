package com.cheong.agenticai.service;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public interface ParkingSlotCacheService {

    Mono<Boolean> save(String key, ParkingSlot slot, Duration expiry);

    Flux<ParkingSlot> findAll();

    Mono<Long> saveIntoSet(ParkingSlot ... parkingSlots);

    Mono<ParkingSlot> get(String key);

    Mono<Boolean> delete(String key);

    Mono<Duration> getTtl(String key);
}
