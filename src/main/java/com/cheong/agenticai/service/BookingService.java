package com.cheong.agenticai.service;

import com.cheong.agenticai.agent.AvailabilityAgent;
import com.cheong.agenticai.agent.PaymentAgent;
import com.cheong.agenticai.agent.ReservationAgent;
import com.cheong.agenticai.dto.BookingRequest;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BookingService {

    private final SupervisorAgent supervisorAgent;

    public BookingService(AvailabilityAgent availabilityAgent,
                          ReservationAgent reservationAgent,
                          PaymentAgent paymentAgent,
                          ChatModel chatModel) {
        this.supervisorAgent = AgenticServices
                .supervisorBuilder()
                .chatModel(chatModel)
                .subAgents(availabilityAgent, reservationAgent, paymentAgent)
                .responseStrategy(SupervisorResponseStrategy.SUMMARY)
//                .errorHandler(errorContext -> {
//                    return ErrorRecoveryResult.result(errorContext.exception().getMessage());
//                })
                .build();
    }

    public Mono<String> bookSlot(BookingRequest bookingRequest){

        String prompt = String.format(
                "IMPORTANT: English only."+
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

        return Mono.fromCallable(()-> supervisorAgent.invoke(prompt))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
