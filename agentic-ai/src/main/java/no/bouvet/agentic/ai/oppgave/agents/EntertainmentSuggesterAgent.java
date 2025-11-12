package no.bouvet.agentic.ai.oppgave.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave.domain.Entertainment;

/**
 * Agent responsible for suggesting suitable entertainment or activities for an event.
 */
public interface EntertainmentSuggesterAgent {

    @UserMessage("""
        """)
    @Agent("Suggests entertainment options based on event type, venue, and audience size.")
    Entertainment suggest(@V("venueName") String venueName,
                          @V("venueType") String venueType,
                          @V("eventType") String eventType,
                          @V("amountOfPersons") int amountOfPersons,
                          @V("city") String city);
}

