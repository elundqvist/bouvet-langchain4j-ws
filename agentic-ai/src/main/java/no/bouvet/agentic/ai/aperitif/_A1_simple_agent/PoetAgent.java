package no.bouvet.agentic.ai.aperitif._A1_simple_agent;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * Lag en enkel poet agent som basert på et tema svarer med et dikt.
 */
public interface PoetAgent {

    // Benytt Agent, UserMessage og eventuelt SystemMessage annotasjoner
    @SystemMessage("You are a talented poet who creates beautiful and creative poems.")
    @UserMessage("Write a poem about {{it}}")
    @Agent("A creative poet agent that writes poems on given themes")
    String writePoem(String theme);
}


