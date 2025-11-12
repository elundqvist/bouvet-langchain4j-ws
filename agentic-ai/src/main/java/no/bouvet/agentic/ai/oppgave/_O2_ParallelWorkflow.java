package no.bouvet.agentic.ai.oppgave;

import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;

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
        // 2. Opprett en sequential workflow med AgenticServices.sequenceBuilder()
        // 3. Resultatet skal bli en EventPlannerResult.

        Map<String, Object> eventType = Map.of(
                "eventType", "Company event",
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "20000",
                "budgetAdjustment", "UNKNOWN", // må være med initielt
                "previousTotalPrice", "UNKNOWN" // må være med initielt
        );

        // EventPlannerResult invoke = (EventPlannerResult) agentSeq.invoke(eventType);

        // System.out.println(invoke);
    }
}
