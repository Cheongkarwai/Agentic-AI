package com.cheong.agenticai.agent;


import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AvailabilityAgent {

    @SystemMessage("You are an availability agent that checks if parking slots are available for booking.")
    @UserMessage("""
        Check if slot number {{slotNo}} is available from {{startDateTime}} until {{endDateTime}}.
        Return true if the slot is available (not booked), false if it is already booked.
        """)
    @Agent("An agent that checks availability of parking slots (English)")
    String checkAvailability(@V("slotNo") String slotNo,
                             @V("startDateTime") String startDateTime,
                             @V("endDateTime") String endDateTime);
}
