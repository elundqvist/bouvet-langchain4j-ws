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
        Suggest a suitable menu or catering option for a {{eventType}} at {{venue}} in {{city}} for {{amountOfPersons}} persons.
        Consider the venue type and location when making your suggestion.
        {{#if context}}
        Additional context: {{context}}
        {{/if}}
        Provide a menu name, description, and price per person in NOK.
        """)
    @Agent("Suggests suitable menus or catering options based on venue, event type, and group size.")
    Menu suggest(@V("venue") Venue venue,
                 @V("eventType") String eventType,
                 @V("city") String city,
                 @V("amountOfPersons") int amountOfPersons,
                 @V("context") String context);
}
