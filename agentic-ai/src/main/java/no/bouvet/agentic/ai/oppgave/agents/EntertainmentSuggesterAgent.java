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
        The user is organizing a {{eventType}} at {{venueName}} ({{venueType}}) in {{city}}
        for approximately {{amountOfPersons}} guests.

        Suggest one appropriate entertainment option or activity that fits the event type,
        venue, and group size.

        Respond strictly in JSON matching this structure:
        {
          "name": "string",
          "type": "string",
          "description": "string",
          "estimatedCost": number
        }

        Keep it realistic and suitable for the specified event context.
        Do not include any extra commentary or text outside the JSON.
        """)
    @Agent("Suggests entertainment options based on event type, venue, and audience size.")
    Entertainment suggest(@V("venueName") String venueName,
                          @V("venueType") String venueType,
                          @V("eventType") String eventType,
                          @V("amountOfPersons") int amountOfPersons,
                          @V("city") String city);
}

