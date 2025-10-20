package com.cheong.agenticai.config;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, BookingSlot> bookingSlotRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
            ObjectMapper objectMapper
    ) {

        Jackson2JsonRedisSerializer<BookingSlot> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, BookingSlot.class);
        RedisSerializationContext<String, BookingSlot> serializationContext =
                RedisSerializationContext.<String, BookingSlot>newSerializationContext()
                        .key(RedisSerializer.string())
                        .value(valueSerializer)
                        .hashKey(RedisSerializer.string())
                        .hashValue(RedisSerializer.json())
                        .build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, ParkingSlot> parkingSlotRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
            ObjectMapper objectMapper
    ) {

        Jackson2JsonRedisSerializer<ParkingSlot> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, ParkingSlot.class);
        RedisSerializationContext<String, ParkingSlot> serializationContext =
                RedisSerializationContext.<String, ParkingSlot>newSerializationContext()
                        .key(RedisSerializer.string())
                        .value(valueSerializer)
                        .hashKey(RedisSerializer.string())
                        .hashValue(RedisSerializer.json())
                        .build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, serializationContext);
    }
}
