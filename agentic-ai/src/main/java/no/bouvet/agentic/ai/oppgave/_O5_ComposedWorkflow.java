package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.*;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;

/**
 * De 4 ulike workflows som du nå har utviklet kan kombineres som du selv ønsker.
 *
 * Sett opp en composed workflow basert på de 4 foregående oppgavene.
 */
public class _O5_ComposedWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        McpProvider mcpProvider = new McpProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var controlAgent = provider.provideControlAgent(mcpProvider.mcp());
        var entertainmentAgent = provider.provideEntertainmentAgent();

        // Create a loop workflow for budget control
        UntypedAgent budgetLoopAgent = AgenticServices.loopBuilder()
                .subAgents(menuAgent, controlAgent)
                .outputKey("budgetStatus")
                .beforeAgentInvocation(agentRequest -> {
                    AgenticScope agenticScope = agentRequest.agenticScope();
                    if (agenticScope.readState("context") == null) {
                        agenticScope.writeState("context", "none");
                    }
                })
                .exitCondition(agenticScope -> {
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");
                    
                    if (budgetStatus != null && !budgetStatus.budgetOk()) {
                        Menu menu = (Menu) agenticScope.readState("menu");
                        agenticScope.writeState("context", "Previous price per person was %s, reduce the price!"
                                .formatted(menu.pricePerPerson()));
                    }
                    
                    return budgetStatus != null && budgetStatus.budgetOk();
                })
                .description("Budget control loop")
                .maxIterations(5)
                .build();

        // Create a parallel workflow for entertainment
        UntypedAgent parallelAgent = AgenticServices.parallelBuilder()
                .subAgents(entertainmentAgent)
                .description("Parallel entertainment agent")
                .build();

        // Create a conditional workflow that combines everything
        UntypedAgent composedWorkflow = AgenticServices.conditionalBuilder()
                .subAgents(agenticScope -> true, venueAgent, budgetLoopAgent)
                .subAgents(agenticScope -> {
                    // Only run entertainment for events with 20+ persons
                    Object amountOfPersons = agenticScope.readState("amountOfPersons");
                    if (amountOfPersons instanceof String) {
                        return Integer.parseInt((String) amountOfPersons) >= 20;
                    } else if (amountOfPersons instanceof Integer) {
                        return (Integer) amountOfPersons >= 20;
                    }
                    return true;
                }, parallelAgent)
                .outputKey("event")
                .output(agenticScope -> {
                    Venue venue = (Venue) agenticScope.readState("venue");
                    Menu menu = (Menu) agenticScope.readState("menu");
                    Entertainment entertainment = (Entertainment) agenticScope.readState("entertainment");
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");
                    return new EventPlannerResult(venue, menu, entertainment, budgetStatus);
                })
                .build();

        Map<String, Object> eventType = Map.of(
                "eventType", "Company event",
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "15000",
                "context", "none"
        );

        EventPlannerResult result = (EventPlannerResult) composedWorkflow.invoke(eventType);
        
        System.out.println("===== Event Plan Result =====");
        System.out.println(result);
    }
}
