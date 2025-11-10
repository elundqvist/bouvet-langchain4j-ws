package no.bouvet.agentic.ai.oppgave.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave.domain.Menu;
import no.bouvet.agentic.ai.oppgave.domain.Venue;
import org.jspecify.annotations.Nullable;

/**
 * Agent responsible for suggesting a menu or catering option for an event.
 */
public interface MenuSuggesterAgent {

    @UserMessage("""
        Based on the venue {{venue}} in {{city}} and the event type {{eventType}},
        suggest a suitable menu or catering option for {{amountOfPersons}} guests.

        Respond strictly in JSON matching this structure:
        {
          "type": "string",
          "description": "string",
          "pricePerPerson": number
        }
        
        Budget status: {{context}}.
        
        Keep the suggestion realistic and aligned with the venue type and event context.
        Price per person is in currency NOK.
        Do not include any text outside the JSON.
        If budgetOk equals "false", choose a cheaper or smaller option.
        """)
    @Agent("Suggests suitable menus or catering options based on venue, event type, and group size.")
    Menu suggest(@V("venue") Venue venue,
                 @V("eventType") String eventType,
                 @V("city") String city,
                 @V("amountOfPersons") int amountOfPersons,
                 @V("context") String context);
}
