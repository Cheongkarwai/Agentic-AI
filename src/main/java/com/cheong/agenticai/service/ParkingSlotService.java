package com.cheong.agenticai.service;

import com.cheong.agenticai.dto.ParkingSlotDTO;
import com.cheong.agenticai.model.ParkingSlot;
import com.cheong.agenticai.repository.ParkingSlotRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Service
public class ParkingSlotService {

    private final ParkingSlotCacheService parkingSlotCacheService;

    private final ParkingSlotRepository parkingSlotRepository;

    public ParkingSlotService(ParkingSlotCacheService parkingSlotCacheService,
                              ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotCacheService = parkingSlotCacheService;
        this.parkingSlotRepository = parkingSlotRepository;
    }

    public Mono<ParkingSlot> getParkingSlot(String slotNo){
        return parkingSlotCacheService.get(slotNo)
                .switchIfEmpty(parkingSlotRepository.findBySlotNoEquals(slotNo));
    }

    public Flux<ParkingSlotDTO> findAll() {

        Flux<ParkingSlot> slots = parkingSlotRepository.findAll().cache();

        Mono<Void> saveToCache = slots
                .buffer(10)
                .flatMap(parkingSlots -> parkingSlotCacheService.saveIntoSet(parkingSlots.toArray(new ParkingSlot[0])))
                .then();

        Flux<ParkingSlot> findFromCache = Mono.when(saveToCache)
                .thenMany(slots);

        return parkingSlotCacheService.findAll()
                .doOnNext(parkingSlot -> System.out.println("Parking slot found in cache: " + parkingSlot.getSlotNo()))
                .switchIfEmpty(findFromCache)
                .map(parkingSlot -> new ParkingSlotDTO(parkingSlot.getId(), parkingSlot.getSlotNo()));
    }

    public Mono<ParkingSlotDTO> findBySlotNo(String slotNo) {
        return parkingSlotCacheService.get(slotNo)
                .map(parkingSlot -> new ParkingSlotDTO(parkingSlot.getId(), parkingSlot.getSlotNo()));
    }
}
