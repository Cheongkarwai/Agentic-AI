package com.cheong.agenticai.service;

import com.cheong.agenticai.agent.AvailabilityAgent;
import com.cheong.agenticai.agent.PaymentAgent;
import com.cheong.agenticai.agent.ReservationAgent;
import com.cheong.agenticai.dto.BookingRequest;
import com.cheong.agenticai.model.BookingSlot;
import com.cheong.agenticai.repository.BookingSlotRepository;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.V;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class BookingService {

    private final SupervisorAgent supervisorAgent;
    private final BookingSlotService bookingSlotService;

    public BookingService(AvailabilityAgent availabilityAgent,
                          ReservationAgent reservationAgent,
                          PaymentAgent paymentAgent,
                          ChatModel chatModel,
                          BookingSlotService bookingSlotService) {
        this.supervisorAgent = AgenticServices
                .supervisorBuilder()
                .chatModel(chatModel)
                .subAgents(availabilityAgent, reservationAgent, paymentAgent)
                .responseStrategy(SupervisorResponseStrategy.LAST)
//                .errorHandler(errorContext -> {
//                    return ErrorRecoveryResult.result(errorContext.exception().getMessage());
//                })
                .build();
        this.bookingSlotService = bookingSlotService;
    }

    public Mono<String> bookSlot(BookingRequest bookingRequest) {

        String prompt = String.format(
                "IMPORTANT: English only." +
                        "User %s wants to book a parking slot." +
                        "Check availability for slot %s from %s to %s. " +
                        "IMPORTANT: Only if the slot is available, proceed to reserve it. " +
                        "If the slot is NOT available, stop immediately and report that the slot is unavailable. " +
                        "Do not attempt to reserve an unavailable slot." +
                        "Once reserved, initiate the payment for the parking slot.",
                bookingRequest.getUserId(),
                bookingRequest.getSlotNo(),
                bookingRequest.getStartDateTime(),
                bookingRequest.getEndDateTime());

        return Mono.fromCallable(() -> supervisorAgent.invoke(prompt))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> extendBooking(String bookingId, int additionalMinutes) {
        return bookingSlotService.findBookedSlotById(bookingId)
                .switchIfEmpty(Mono.defer(()->Mono.error(new RuntimeException("Booking slot not found"))))
                .filter(this::shouldAutoExtend)
                .flatMap(bookingSlot -> {
                    bookingSlot.setEndDateTime(bookingSlot.getEndDateTime().plusMinutes(additionalMinutes));
                    return bookingSlotService.save(bookingSlot);
                })
                .then();
    }

    private boolean shouldAutoExtend(BookingSlot booking) {
        LocalDateTime now = LocalDateTime.now();
        Duration timeRemaining = Duration.between(now, booking.getEndDateTime());
        return timeRemaining.toMinutes() < 15;
    }
}
