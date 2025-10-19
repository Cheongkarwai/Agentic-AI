package com.cheong.agenticai.agent;

import dev.langchain4j.service.UserMessage;

//Orchestration Layer
public interface OrchestratorAgent {


    void process(@UserMessage String message);
}
