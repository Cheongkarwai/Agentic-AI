package com.cheong.agenticai.config;

import com.cheong.agenticai.processor.ProcessorFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener {

    private final ReactiveRedisMessageListenerContainer listenerContainer;

    private final ProcessorFactory processorFactory;

    public RedisKeyExpirationListener(ReactiveRedisMessageListenerContainer listenerContainer,
                                      ProcessorFactory processorFactory) {
        this.listenerContainer = listenerContainer;
        this.processorFactory = processorFactory;
    }

    @PostConstruct
    public void init() {
        listenerContainer.receive(PatternTopic.of("__keyevent@*__:expired"))
                .map(ReactiveSubscription.ChannelMessage::getMessage)
                .flatMap(expiredKey-> processorFactory.getProcessor(expiredKey).process(expiredKey))
                .subscribe();
    }
}
