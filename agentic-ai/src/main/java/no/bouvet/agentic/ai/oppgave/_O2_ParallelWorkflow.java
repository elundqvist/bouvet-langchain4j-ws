package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.*;

import java.util.Map;


public class _O2_ParallelWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var entertainmentAgent = provider.provideEntertainmentAgent();

        UntypedAgent agentParallel = AgenticServices.parallelBuilder()
                .subAgents(menuAgent, entertainmentAgent)
                .outputKey("event")
                .build();

        UntypedAgent agentSeq = AgenticServices.sequenceBuilder()
                .subAgents(venueAgent, agentParallel)
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
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "20000",
                "budgetAdjustment", "UNKNOWN",
                "previousTotalPrice", "UNKNOWN"
        );

        EventPlannerResult invoke = (EventPlannerResult) agentSeq.invoke(eventType);

        System.out.println(invoke);
    }
}
