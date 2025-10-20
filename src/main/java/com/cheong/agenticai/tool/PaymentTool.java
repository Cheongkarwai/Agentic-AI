package com.cheong.agenticai.tool;

import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.model.Transaction;
import com.cheong.agenticai.repository.BookingSlotRepository;
import com.cheong.agenticai.repository.ParkingSlotRepository;
import com.cheong.agenticai.repository.TransactionRepository;
import com.cheong.agenticai.service.BookingSlotService;
import com.cheong.agenticai.service.ParkingSlotService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class PaymentTool {

    private final ParkingSlotService parkingSlotService;

    private final BookingSlotRepository bookingSlotRepository;

    private final TransactionRepository transactionRepository;
    private final BookingSlotService bookingSlotService;

    public PaymentTool(ParkingSlotService parkingSlotService,
                       BookingSlotRepository bookingSlotRepository,
                       TransactionRepository transactionRepository, BookingSlotService bookingSlotService) {
        this.parkingSlotService = parkingSlotService;
        this.bookingSlotRepository = bookingSlotRepository;
        this.transactionRepository = transactionRepository;
        this.bookingSlotService = bookingSlotService;
    }

    @Tool("""
            Initiate payment for a parking slot. This should ONLY be called after reserveSlot returns BOOKING_ID.
            Returns payment redirect url if successful, error message otherwise.
            """)
    public String initiatePayment(String slotNo) {

        String url = null;

        try {
            List<BookingSlot.Status> statuses = List.of(BookingSlot.Status.PENDING);
            url = parkingSlotService.getParkingSlot(slotNo)
                    .flatMap(parkingSlot -> bookingSlotRepository.findFirstByParkingSlotIdEqualsAndStatusInAndTransactionIdIsNull(parkingSlot.getId(), statuses))
                    .flatMap(bookingSlot -> {
                        Transaction transaction = Transaction.builder()
                                .userId(bookingSlot.getUserId())
                                .amount(BigDecimal.valueOf(200))
                                .status(Transaction.Status.INITIATED)
                                .transactionDate(LocalDateTime.now())
                                .build();
                        return transactionRepository.save(transaction)
                                .map(transaction1 -> {
                                    bookingSlot.setTransactionId(transaction1.getId());
                                    return bookingSlot;
                                })
                                .flatMap(savedBookingSlot -> bookingSlotService.save(savedBookingSlot)
                                        .thenReturn("http://localhost:8080/payment?transactionId=" + savedBookingSlot.getTransactionId() + "&amount=200"));
                    })
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR - Failed to initiate payment: " + e.getMessage();
        }

        return url;
    }
}
