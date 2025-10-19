package com.cheong.agenticai.service;

import com.cheong.agenticai.agent.AvailabilityAgent;
import com.cheong.agenticai.agent.PaymentAgent;
import com.cheong.agenticai.agent.ReservationAgent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private UntypedAgent untypedAgent;

    public BookingService(AvailabilityAgent availabilityAgent,
                          ReservationAgent reservationAgent,
                          PaymentAgent paymentAgent) {
        this.untypedAgent = AgenticServices.sequenceBuilder()
                .subAgents(availabilityAgent, reservationAgent, paymentAgent)
                .build();


    }
}
