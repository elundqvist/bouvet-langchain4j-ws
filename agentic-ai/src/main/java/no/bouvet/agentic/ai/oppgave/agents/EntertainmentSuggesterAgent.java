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
        Suggest suitable entertainment or activities for a {{eventType}} at {{venueName}} (a {{venueType}}) in {{city}} for {{amountOfPersons}} persons.
        Consider the venue type and audience size when making your suggestion.
        Provide an activity name, description, and estimated price in NOK.
        """)
    @Agent("Suggests entertainment options based on event type, venue, and audience size.")
    Entertainment suggest(@V("venueName") String venueName,
                          @V("venueType") String venueType,
                          @V("eventType") String eventType,
                          @V("amountOfPersons") int amountOfPersons,
                          @V("city") String city);
}

