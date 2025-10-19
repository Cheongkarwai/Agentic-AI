package com.cheong.agenticai.config;

import com.cheong.agenticai.agent.AvailabilityAgent;
import com.cheong.agenticai.agent.OrchestratorAgent;
import com.cheong.agenticai.agent.PaymentAgent;
import com.cheong.agenticai.agent.ReservationAgent;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    @Bean
    public ReservationAgent reservationAgent(ChatModel chatModel) {
        return AgenticServices.agentBuilder(ReservationAgent.class)
                .chatModel(chatModel)
                .outputName("reservationAgent")
                .build();
    }

    @Bean
    public PaymentAgent paymentAgent(ChatModel chatModel){
        return AgenticServices.agentBuilder(PaymentAgent.class)
                .chatModel(chatModel)
                .outputName("paymentAgent")
                .build();
    }

    @Bean
    public AvailabilityAgent availabilityAgent(ChatModel chatModel){
        return AgenticServices.agentBuilder(AvailabilityAgent.class)
                .chatModel(chatModel)
                .outputName("availabilityAgent")
                .build();
    }

}
