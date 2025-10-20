package com.cheong.agenticai.service;

import com.cheong.agenticai.dto.BookingSlotDTO;
import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.repository.BookingSlotRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

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
                                Duration.between(LocalDateTime.now(), bookingSlot.getEndDateTime()))
                        .thenReturn(bookingSlot));
    }

    public Flux<BookingSlotDTO> findAll() {
        return bookingSlotRepository.findAll()
                .map(bookingSlot -> new BookingSlotDTO(bookingSlot.getId(),
                        bookingSlot.getParkingSlotId(),
                        bookingSlot.getUserId(),
                        bookingSlot.getStartDateTime(),
                        bookingSlot.getEndDateTime()));
    }

    public Mono<BookingSlot> cleanUpSlot(String expiredKey) {
        return bookingSlotCacheService.delete(expiredKey)
                .then(bookingSlotRepository.findById(expiredKey.split(":")[1])
                        .map(bookingSlot -> {
                            bookingSlot.setStatus(bookingSlot.getTransactionId() != null ? BookingSlot.Status.COMPLETED : BookingSlot.Status.CANCELLED);
                            return bookingSlot;
                        })
                        .flatMap(bookingSlotRepository::save));
    }
}
