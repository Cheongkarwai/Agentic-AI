package com.cheong.agenticai.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface PaymentAgent {

    @SystemMessage("""
            You are a payment agent responsible for processing payments for reserved parking slots.
            Process the payment for the given slot number and return a confirmation message.
            If the payment fails, explain the reason clearly.
            """)
    @UserMessage("""
            Process payment for slot number: {{slotNo}} once reserved
            """)
    @Agent("An agent that processes payments for reserved parking slots")
    String initiatePayment(@V("slotNo") String slotNo);
}
