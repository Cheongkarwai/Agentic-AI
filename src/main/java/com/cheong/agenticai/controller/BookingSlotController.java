package com.cheong.agenticai.controller;

import com.cheong.agenticai.dto.BookingSlotDTO;
import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.service.BookingSlotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class BookingSlotController {

    private final BookingSlotService bookingSlotService;

    public BookingSlotController(BookingSlotService bookingSlotService) {
        this.bookingSlotService = bookingSlotService;
    }

    @GetMapping("/booking-slots")
    public Flux<BookingSlotDTO> findAll(){
        return bookingSlotService.findAll();
    }
}
