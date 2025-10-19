package com.cheong.agenticai.tool;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import com.cheong.agenticai.repository.BookingSlotRepository;
import com.cheong.agenticai.repository.ParkingSlotRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class ParkingSlotReservationTool {

    private final ParkingSlotRepository parkingSlotRepository;
    private final BookingSlotRepository bookingSlotRepository;

    public ParkingSlotReservationTool(
            ParkingSlotRepository parkingSlotRepository,
            BookingSlotRepository bookingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.bookingSlotRepository = bookingSlotRepository;
    }

    @Tool("""
    Check the parking slot availability based on given slotNo & given startDateTime and endDateTime
    """)
    public String checkAvailability(@P("slotNo") String slotNo,
                              @P("startDateTime") String startDateTime,
                              @P("endDateTime") String endDateTime) {

        List<BookingSlot.Status> statuses = List.of(BookingSlot.Status.CONFIRMED, BookingSlot.Status.PENDING);
        LocalDateTime startDateTimeLocal = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endDateTimeLocal = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        log.info("Checking availability for slotNo {} from {} to {}", slotNo, startDateTime, endDateTime);

        try {
            Integer totalBookingSlotCount = parkingSlotRepository.findBySlotNoEquals(slotNo)
                    .doOnNext(parkingSlot -> log.info("Parking slot found for slotNo {}", slotNo))
                    .flatMap(parkingSlot -> {
                        return bookingSlotRepository.countBookingSlotByParkingSlotIdEqualsAndStatusInAndStartDateTimeIsGreaterThanEqualAndEndDateTimeIsLessThanEqual
                                        (slotNo, statuses, startDateTimeLocal, endDateTimeLocal)
                                .doOnNext(count -> log.info("Total booking slot count for slotNo {} is {}", slotNo, count));
                    })
                    .defaultIfEmpty(0)
                    .block();

            if (totalBookingSlotCount == null || totalBookingSlotCount <= 0) {
                log.info("Slot {} is AVAILABLE", slotNo);
                return "AVAILABLE";
            } else {
                log.info("Slot {} is NOT_AVAILABLE - {} existing bookings", slotNo, totalBookingSlotCount);
                return "NOT_AVAILABLE - Slot already has " + totalBookingSlotCount + " booking(s) in this time period";
            }
        } catch (Exception e) {
            log.error("Error checking availability for slotNo {}", slotNo, e);
            return "ERROR - Failed to check availability: " + e.getMessage();
        }
    }

}
