package com.cheong.agenticai.processor;

import reactor.core.publisher.Mono;

public interface Processor {

    Mono<Void> process(String expiredKey);

    ProcessorType getType();

    public enum ProcessorType {
        CLEAN_UP_BOOKING_SLOT,
    }
}
