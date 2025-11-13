package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.Entertainment;
import no.bouvet.agentic.ai.oppgave.domain.EventPlannerResult;
import no.bouvet.agentic.ai.oppgave.domain.Menu;
import no.bouvet.agentic.ai.oppgave.domain.Venue;

import java.util.Map;

/**
 * Kjør menuAgent og entertainmentAgent parallelt i en egen workflow.
 */
public class _O2_ParallelWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var entertainmentAgent = provider.provideEntertainmentAgent();

        // 1. Opprett en parallel workflow med AgenticServices.parallelBuilder()
        UntypedAgent parallelAgent = AgenticServices.parallelBuilder()
                .subAgents(menuAgent, entertainmentAgent)
                .description("Parallel execution of menu and entertainment agents")
                .build();

        // 2. Opprett en sequential workflow med AgenticServices.sequenceBuilder()
        UntypedAgent agentSeq = AgenticServices.sequenceBuilder()
                .subAgents(venueAgent, parallelAgent)
                .outputKey("event")
                .output(agenticScope -> {
                    Venue venue = (Venue) agenticScope.readState("venue");
                    Menu menu = (Menu) agenticScope.readState("menu");
                    Entertainment entertainment = (Entertainment) agenticScope.readState("entertainment");
                    return new EventPlannerResult(venue, menu, entertainment, null);
                })
                .build();

        // 3. Resultatet skal bli en EventPlannerResult.
        Map<String, Object> eventType = Map.of(
                "eventType", "Company event",
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "20000",
                "budgetAdjustment", "UNKNOWN", // må være med initielt
                "previousTotalPrice", "UNKNOWN", // må være med initielt
                "context", "none"
        );

        EventPlannerResult invoke = (EventPlannerResult) agentSeq.invoke(eventType);

        System.out.println(invoke);
    }
}
