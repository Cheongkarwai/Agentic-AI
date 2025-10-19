package com.cheong.agenticai.config;

import com.cheong.agenticai.agent.AvailabilityAgent;
import com.cheong.agenticai.agent.ReservationAgent;
import com.cheong.agenticai.tool.ParkingSlotReservationTool;
import com.cheong.agenticai.tool.ReservationTool;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public ReservationAgent reservationAgent(ChatModel chatModel, ReservationTool reservationTool) {
        return AgenticServices.agentBuilder(ReservationAgent.class)
                .chatModel(chatModel)
                .tools(reservationTool)
                .outputName("reservationAgent")
                .build();
    }

    @Bean
    public AvailabilityAgent availabilityAgent(ChatModel chatModel,
                                               ParkingSlotReservationTool parkingSlotAvailabilityTool){
        return AgenticServices.agentBuilder(AvailabilityAgent.class)
                .chatModel(chatModel)
                .tools(parkingSlotAvailabilityTool)
                .outputName("availabilityAgent")
                .build();
    }


}
