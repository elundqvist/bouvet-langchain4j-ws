package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.*;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;

/**
 * Hvis bruker skal planlegge et event under 20 personer, så er det ikke behov for underholdning.
 * Sørg for at conditional workflow'en under dropper entertainmentAgent i dette tilfellet.
 */
public class _O4_ConditionalWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var entertainmentAgent = provider.provideEntertainmentAgent();

        UntypedAgent agentSeq = AgenticServices.conditionalBuilder()
                .subAgents(venueAgent, menuAgent, entertainmentAgent)
                .outputKey("event")
                .output(agenticScope -> {
                    Venue venue = (Venue) agenticScope.readState("venue");
                    Menu menu = (Menu) agenticScope.readState("menu");
                    Entertainment entertainment = (Entertainment) agenticScope.readState("entertainment");
                    return new EventPlannerResult(venue, menu, entertainment, null);
                })
                .build();

        Map<String, Object> eventType = Map.of(
                "eventType", "Company event",
                "amountOfPersons", "15",
                "city", "Oslo",
                "budget", "8000",
                "context", "none"
        );

        EventPlannerResult invoke = (EventPlannerResult) agentSeq.invoke(eventType);

        System.out.println(invoke);
    }
}
