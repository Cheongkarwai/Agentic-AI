package com.cheong.agenticai.controller;

import com.cheong.agenticai.dto.BookingRequest;
import com.cheong.agenticai.service.BookingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking")
    public Mono<String> bookSlot(@Validated @RequestBody BookingRequest bookingRequest){
        return bookingService.bookSlot(bookingRequest);
    }
}
