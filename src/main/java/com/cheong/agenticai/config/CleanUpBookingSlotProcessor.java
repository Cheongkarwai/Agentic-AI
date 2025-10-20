package com.cheong.agenticai.config;

import com.cheong.agenticai.service.BookingSlotService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CleanUpBookingSlotProcessor implements Processor{

    private final BookingSlotService bookingSlotService;

    public CleanUpBookingSlotProcessor(BookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

    @Override
    public Mono<Void> process(String expiredKey) {
        return bookingSlotService.cleanUpSlot(expiredKey)
                .then();
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.CLEAN_UP_BOOKING_SLOT;
    }
}
