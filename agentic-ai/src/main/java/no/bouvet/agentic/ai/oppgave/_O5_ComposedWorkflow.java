package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.*;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;


public class _O5_ComposedWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        McpProvider mcpProvider = new McpProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var controlAgent = provider.provideControlAgent(mcpProvider.mcp());
        var entertainmentAgent = provider.provideEntertainmentAgent();

        UntypedAgent budgetAgent = AgenticServices.loopBuilder()
                .subAgents(menuAgent, controlAgent)
                .outputKey("budgetStatus")
                .exitCondition(agenticScope -> {
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");

                    if (budgetStatus != null && !budgetStatus.budgetOk()) {
                        Menu menu = (Menu) agenticScope.readState("menu");
                        agenticScope.writeState("context", "Previous price per person was %s, reduce the price!"
                                .formatted(menu.pricePerPerson()));
                    }

                    return budgetStatus != null && budgetStatus.budgetOk();
                })
                .description("Fetch menu and control budget agents")
                .maxIterations(5)
                .build();

        UntypedAgent agentParallel = AgenticServices.parallelBuilder()
                .subAgents(budgetAgent, entertainmentAgent)
                .outputKey("event")
                .build();

        UntypedAgent agentSeq = AgenticServices.sequenceBuilder()
                .subAgents(venueAgent, agentParallel)
                .outputKey("event")
                .output(agenticScope -> {
                    Venue venue = (Venue) agenticScope.readState("venue");
                    Menu menu = (Menu) agenticScope.readState("menu");
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");
                    Entertainment entertainment = (Entertainment) agenticScope.readState("entertainment");
                    return new EventPlannerResult(venue, menu, entertainment, budgetStatus);
                })
                .build();

        Map<String, Object> eventType = Map.of(
                "eventType", "Company event",
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "10000",
                "context", "none"
        );


        EventPlannerResult invoke = (EventPlannerResult) agentSeq.invoke(eventType);

        System.out.println(invoke);
    }
}
