package no.bouvet.agentic.ai.oppgave.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave.domain.Venue;

/**
 * Agent responsible for suggesting event venues.
 */
public interface VenueSuggesterAgent {

    @SystemMessage("""
            """)
    @UserMessage("""
            """)
    @Agent("Suggests venues for events based on city, event type, and group size.")
    Venue suggest(@V("eventType") String eventType,
                  @V("amountOfPersons") int amountOfPersons,
                  @V("city") String city);
}

