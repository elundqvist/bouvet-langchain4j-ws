package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.agents.EventPlanningSupervisor;
import no.bouvet.agentic.ai.oppgave.domain.*;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;

/**
 * Benytt EventPlanningSupervisor til orkestrere alle agentene.
 * PS! det er også mulig å kombinere supervisor agents med andre workflows.
 *
 * Eksempler: https://github.com/langchain4j/langchain4j-examples/tree/main/agentic-tutorial/src/main/java/_7_supervisor_orchestration
 * Dokumentasjon: https://docs.langchain4j.dev/tutorials/agents#pure-agentic-ai
 */
public class _O6_SupervisorAgent {
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
                .beforeAgentInvocation(agentRequest -> {
                    AgenticScope agenticScope = agentRequest.agenticScope();
                    agenticScope.writeState("context", "none");
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
                .description("Fetch menu and control budget agents")
                .maxIterations(5)
                .build();

        // Lag en EventPlanningSupervisor med AgenticServices.supervisorBuilder(EventPlanningSupervisor.class)...
        //..

        //String invoke1 = theEventPlanningSupervisor.invoke("hi, can you plan an company event for about 50 persons in Oslo area. The budget is 20000 NOK.");
        //String invoke2 = theEventPlanningSupervisor.invoke("Oops, i changed my mind, we want the event in Drammen, same budget!");

        System.out.println("***** Invoke 1");
        //System.out.println(invoke1);
        //System.out.println("***** Invoke 2");
        //System.out.println(invoke2);
    }
}
