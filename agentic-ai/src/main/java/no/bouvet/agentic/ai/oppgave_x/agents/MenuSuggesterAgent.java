package no.bouvet.agentic.ai.oppgave_x.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave_x.domain.Menu;

/**
 * Agent responsible for suggesting a menu or catering option for an event.
 */
public interface MenuSuggesterAgent {

    @UserMessage("""
        Based on the {{venueType}} {{venueName}} in {{city}} and the event type {{eventType}},
        suggest a suitable menu or catering option for {{amountOfPersons}} guests.

        Respond strictly in JSON matching this structure:
        {
          "type": "string",
          "description": "string",
          "pricePerPerson": number
        }
        
        Budget status: {{budgetAdjustment}}. Previous total prise was {{previousTotalPrice}}, try to keep below that total price.
        If budgetAdjustment equals "reduce", propose a cheaper or simpler menu.

        Keep the suggestion realistic and aligned with the venue type and event context.
        Price per person is in currency NOK.
        Do not include any text outside the JSON.
        If budgetOk equals "false", choose a cheaper or smaller option.
        """)
    @Agent("Suggests suitable menus or catering options based on venue, event type, and group size.")
    Menu suggest(@V("venueName") String venueName,
                 @V("venueType") String venueType,
                 @V("eventType") String eventType,
                 @V("city") String city,
                 @V("amountOfPersons") int amountOfPersons,
                 @V("budgetAdjustment") String budgetAdjustment,
                 @V("previousTotalPrice") String previousTotalPrice);
}
