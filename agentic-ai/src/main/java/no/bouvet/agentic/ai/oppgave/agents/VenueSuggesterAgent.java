package no.bouvet.agentic.ai.oppgave.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave.domain.Venue;

/**
 * Agent responsible for suggesting event venues.
 */
public interface VenueSuggesterAgent {

    @UserMessage("""
            The user is organizing a {{eventType}} in {{city}} for about {{amountOfPersons}} people.
            Suggest one suitable venue for this type of event.
            
            Respond strictly in JSON matching this structure:
            {
              "name": "string",
              "venueType": "string",
              "description": "string",
              "capacity": number
            }
            
            Do not include food, pricing, or catering details.
            Keep the suggestion realistic and specific to the given city.
            """)
    @Agent("Suggests venues for events based on city, event type, and group size.")
    Venue suggest(@V("eventType") String eventType,
                  @V("amountOfPersons") int amountOfPersons,
                  @V("city") String city);
}

