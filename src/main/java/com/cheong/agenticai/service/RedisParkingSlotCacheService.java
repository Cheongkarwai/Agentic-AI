package com.cheong.agenticai.service;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class RedisParkingSlotCacheService implements ParkingSlotCacheService{

    private final ReactiveRedisTemplate<String, ParkingSlot> redisTemplate;

    @Value("${cache.parking-slot.prefix:parking-slot}")
    private String cachePrefix;

    public RedisParkingSlotCacheService(ReactiveRedisTemplate<String, ParkingSlot> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<Boolean> save(String key, ParkingSlot slot, Duration expiry) {
        return redisTemplate.opsForValue()
                .set(String.format("%s:%s", "parking-slot", key), slot, expiry)
                .flatMap(hasSaved -> {
                    if(!hasSaved){
                        return Mono.error(new RuntimeException("Failed to save parking slot to cache"));
                    }
                    return saveIntoSet(slot)
                            .thenReturn(true);
                });
    }

    @Override
    public Mono<Boolean> saveAll(Iterable<BookingSlot> slots, Duration expiry) {
        return null;
    }

    @Override
    public Flux<ParkingSlot> findAll() {
        return redisTemplate.opsForSet()
                .members(cachePrefix);
    }

    @Override
    public Mono<Long> saveIntoSet(ParkingSlot ... parkingSlots) {
        return redisTemplate.opsForSet()
                .add(cachePrefix, parkingSlots);
    }

    @Override
    public Mono<ParkingSlot> get(String key) {
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
