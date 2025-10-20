package com.cheong.agenticai.controller;

import com.cheong.agenticai.dto.ParkingSlotDTO;
import com.cheong.agenticai.model.ParkingSlot;
import com.cheong.agenticai.service.ParkingSlotService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping("/parking-slots")
    public Flux<ParkingSlotDTO> getAllParkingSlots() {
        return parkingSlotService.findAll();
    }

    @GetMapping("/parking-slots/{slotNo}")
    private Mono<ParkingSlotDTO> findBySlotNo(@PathVariable String slotNo){
        return parkingSlotService.findBySlotNo(slotNo);
    }

}
