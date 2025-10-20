package com.cheong.agenticai.service;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import reactor.core.publisher.Mono;

import java.time.Duration;

public interface BookingSlotCacheService {

    Mono<Boolean> save(String key, BookingSlot slot, Duration expiry);

    Mono<BookingSlot> get(String key);

    Mono<Boolean> delete(String key);

    Mono<Duration> getTtl(String key);
}
