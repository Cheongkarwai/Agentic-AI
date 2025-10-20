package com.cheong.agenticai.config;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProcessorFactory {

    private final Map<Processor.ProcessorType, Processor> processorMap;

    public ProcessorFactory(List<Processor> processors) {
        this.processorMap = processors.stream()
                .collect(Collectors.toMap(
                        Processor::getType,
                        processor -> processor
                ));
    }

    public Processor getProcessor(String expiredKey) {
        String[] keyParts = expiredKey.split(":");
        return switch (keyParts[0]) {
            case "booking-slot"->  processorMap.get(Processor.ProcessorType.CLEAN_UP_BOOKING_SLOT);
            default -> throw new IllegalStateException("Unexpected value: " + keyParts[0]);
        };
    }
}
