package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.domain.*;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;

/**
 * Menu Agent returnerer en pricePerPerson.
 * BudgetControllerAgent benytter en MCP-server for å summere totalsummen for den foreslåtte menyen.
 *
 * Lag en exit condition for loop workflow som sørger for at menuAgent reduserer
 * prisen gjennom hver iterasjon til tutalsummen er under angitt budsjett.
 */
public class _O3_LoopWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        McpProvider mcpProvider = new McpProvider();
        var controlAgent = provider.provideControlAgent(mcpProvider.mcp());
        var menuAgent = provider.provideMenuAgent();

        UntypedAgent budgetAgent = AgenticServices.loopBuilder()
                .subAgents(menuAgent, controlAgent)
                .outputKey("budgetStatus")
                .exitCondition(agenticScope -> {
                    // Kode her ...
                    return true;
                })
                .description("Fetch menu and control budget agents")
                .maxIterations(5)
                .build();

        Map<String, Object> eventType = Map.of(
                "venue", new Venue("Oslo Kongressenter", "Kongressenter", "", 100),
                "eventType", "Company event",
                "amountOfPersons", "50",
                "city", "Oslo",
                "budget", "10000",
                "context", "none"
        );

        BudgetStatus invoke = (BudgetStatus) budgetAgent.invoke(eventType);

        System.out.println(invoke);
    }
}
