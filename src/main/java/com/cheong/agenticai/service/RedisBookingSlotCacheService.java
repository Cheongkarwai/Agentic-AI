package com.cheong.agenticai.service;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class RedisBookingSlotCacheService implements BookingSlotCacheService {

    private final ReactiveRedisTemplate<String, BookingSlot> redisTemplate;

    @Value("${cache.booking-slot.prefix:booking-slot}")
    private String cachePrefix;

    public RedisBookingSlotCacheService(ReactiveRedisTemplate<String, BookingSlot> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Boolean> save(String key, BookingSlot slot, Duration expiry) {
        return redisTemplate.opsForValue()
                .set(String.format("%s:%s", cachePrefix, key), slot, expiry);
    }

    @Override
    public Mono<BookingSlot> get(String key) {
        return redisTemplate.opsForValue()
                .get(String.format("%s:%s", cachePrefix, key));
    }

    @Override
    public Mono<Boolean> delete(String key) {
        return redisTemplate.opsForValue()
                .delete(String.format("%s:%s", cachePrefix, key));
    }

    @Override
    public Mono<Duration> getTtl(String key) {
        return redisTemplate.getExpire(String.format("%s:%s", cachePrefix, key));
    }
}
