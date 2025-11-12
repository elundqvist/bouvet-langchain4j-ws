package no.bouvet.agentic.ai.aperitif._A1_simple_agent;

/**
 * Lag en enkel poet agent som basert på et tema svarer med et dikt.
 */
public interface PoetAgent {

    // Benytt Agent, UserMessage og eventuelt SystemMessage annotasjoner
    String writePoem(String theme);
}


