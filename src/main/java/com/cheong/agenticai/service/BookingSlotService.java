package com.cheong.agenticai.service;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.repository.BookingSlotRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookingSlotService {

    private final BookingSlotRepository bookingSlotRepository;

    private final BookingSlotCacheService bookingSlotCacheService;

    public BookingSlotService(BookingSlotCacheService bookingSlotCacheService,
                              BookingSlotRepository bookingSlotRepository) {
        this.bookingSlotCacheService = bookingSlotCacheService;
        this.bookingSlotRepository = bookingSlotRepository;
    }

    public Mono<BookingSlot> save(BookingSlot slot) {
        return bookingSlotRepository.save(slot)
                .flatMap(bookingSlot -> bookingSlotCacheService.save(bookingSlot.getId(),
                                bookingSlot,
                                Duration.between(bookingSlot.getStartDateTime(), bookingSlot.getEndDateTime()))
                        .thenReturn(bookingSlot));
    }
}
