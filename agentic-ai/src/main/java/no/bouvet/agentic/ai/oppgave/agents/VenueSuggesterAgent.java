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
            You are a venue specialist helping to find perfect event locations.
            Consider the event type, city, and number of attendees when making suggestions.
            Provide detailed and suitable venue recommendations.
            """)
    @UserMessage("""
            Find a suitable venue for a {{eventType}} in {{city}} for {{amountOfPersons}} persons.
            Provide a specific venue name, type, address, and capacity.
            """)
    @Agent("Suggests venues for events based on city, event type, and group size.")
    Venue suggest(@V("eventType") String eventType,
                  @V("amountOfPersons") int amountOfPersons,
                  @V("city") String city);
}

