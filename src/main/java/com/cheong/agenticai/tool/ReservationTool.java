package com.cheong.agenticai.tool;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.ParkingSlot;
import com.cheong.agenticai.repository.BookingSlotRepository;
import com.cheong.agenticai.repository.ParkingSlotRepository;
import com.cheong.agenticai.service.BookingService;
import com.cheong.agenticai.service.BookingSlotCacheService;
import com.cheong.agenticai.service.BookingSlotService;
import com.cheong.agenticai.service.ParkingSlotService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class ReservationTool {

    private final AvailabilityTool availabilityTool;

    private final ParkingSlotService parkingSlotService;

    private final BookingSlotService bookingSlotService;

    public ReservationTool(AvailabilityTool availabilityTool,
                           ParkingSlotService parkingSlotService,
                           BookingSlotService bookingSlotService){
        this.availabilityTool = availabilityTool;
        this.parkingSlotService = parkingSlotService;
        this.bookingSlotService = bookingSlotService;
    }

    @Tool("""
    Reserve a parking slot. This should ONLY be called after checkAvailability returns 'AVAILABLE'.
    Returns booking ID if successful, error message otherwise.
    """)
    public String reserveSlot(@P("slotNo") String slotNo,
                              @P("startDateTime") String startDateTime,
                              @P("endDateTime") String endDateTime,
                              @P("userId") String userId) {

        log.info("Attempting to reserve slot {}", slotNo);

        String availabilityCheck = availabilityTool.checkAvailability(slotNo, startDateTime, endDateTime);

        if (!"AVAILABLE".equals(availabilityCheck)) {
            String message = "RESERVATION_BLOCKED - Cannot reserve slot because: " + availabilityCheck;
            log.warn(message);
            return message;
        }

        try {
            LocalDateTime startDateTimeLocal = LocalDateTime.parse(startDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime endDateTimeLocal = LocalDateTime.parse(endDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            ParkingSlot parkingSlot = parkingSlotService.getParkingSlot(slotNo)
                    .block();

            if (parkingSlot == null) {
                return "ERROR - Slot not found";
            }

            BookingSlot bookingSlot = new BookingSlot();
            bookingSlot.setParkingSlotId(parkingSlot.getId());
            bookingSlot.setStartDateTime(startDateTimeLocal);
            bookingSlot.setEndDateTime(endDateTimeLocal);
            bookingSlot.setStatus(BookingSlot.Status.PENDING);
            bookingSlot.setUserId(userId);

            BookingSlot savedBookingSlot = bookingSlotService.save(bookingSlot)
                    .block();

            String result = "BOOKING_ID:" + savedBookingSlot.getId() ;
            log.info("Slot reserved successfully: {}", result);
            return result;

        } catch (Exception e) {
            log.error("Error reserving slot {}", slotNo, e);
            return "ERROR - Reservation failed: " + e.getMessage();
        }
    }
}
