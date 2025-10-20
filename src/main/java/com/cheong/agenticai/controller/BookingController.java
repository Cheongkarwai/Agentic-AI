package com.cheong.agenticai.controller;

import com.cheong.agenticai.dto.BookingExtensionRequest;
import com.cheong.agenticai.dto.BookingRequest;
import com.cheong.agenticai.service.BookingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Mono<String> bookSlot(@Validated @RequestBody BookingRequest bookingRequest) {
        return bookingService.bookSlot(bookingRequest);
    }

    @PostMapping("/{bookingId}/extend")
    public Mono<Void> extendBooking(
            @PathVariable String bookingId,
            @RequestBody BookingExtensionRequest request) {
        return bookingService.extendBooking(bookingId, request.getAdditionalMinutes());
    }
}
