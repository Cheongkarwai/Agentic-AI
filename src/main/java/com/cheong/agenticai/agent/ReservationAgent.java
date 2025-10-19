package com.cheong.agenticai.agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ReservationAgent {

    @SystemMessage("""
            You are a reservation agent responsible for reserving parking slots.
            Reserve the parking slot for the given slot number and return a confirmation message.
            If the parking slot failed to reserve, explain the reason clearly.
            """)
    @UserMessage("""
            Reserve for slot number: {{slotNo}}
            """)
    @Agent("An agent that reserve for parking slots")
    boolean reserveSlot(@V("slotNo") String slotNo);
}
