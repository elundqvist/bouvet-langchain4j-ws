package no.bouvet.agentic.ai.oppgave;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.agentic.workflow.HumanInTheLoop;
import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.agents.EventPlanningSupervisor;
import no.bouvet.agentic.ai.oppgave.domain.BudgetStatus;
import no.bouvet.agentic.ai.oppgave.domain.Menu;
import no.bouvet.agentic.ai.oppgave.domain.Venue;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

import java.util.Map;
import java.util.Scanner;


public class _O7_HumanInTheLoop {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        var menuAgent = provider.provideMenuAgent();

        HumanInTheLoop humanDecision = AgenticServices.humanInTheLoopBuilder()
                .description("Human validates the event details and budget")
                .inputKey("menu")
                .requestWriter(request -> {
                    System.out.println("The menu is: " + request);
                    System.out.println("Please confirm if the menu looks ok!");
                    System.out.println("Options: Approved (A), Reduce (R)");
                    System.out.print("> ");
                })
                .responseReader(() -> new Scanner(System.in).nextLine())
                .outputKey("humanDecision")
                .build();

        UntypedAgent budgetAgent = AgenticServices.loopBuilder()
                .subAgents(menuAgent, humanDecision)
                .outputKey("menu")
                .exitCondition(agenticScope -> {
                    String humanDecisionStatus = (String) agenticScope.readState("humanDecision");

                    if ("R".equals(humanDecisionStatus)) {
                        Menu menu = (Menu) agenticScope.readState("menu");
                        agenticScope.writeState("context", "Previous price per person was %s, reduce the price!"
                                .formatted(menu.pricePerPerson()));
                    }

                    return "A".equals(humanDecisionStatus);
                })
                .description("Fetch menu and control budget agents")
                .output(agenticScope -> agenticScope.readState("menu"))
                .maxIterations(5)
                .build();

        Map<String, Object> eventType = Map.of(
                "venue", new Venue("Oslo Kongressenter", "Kongressenter", "", 100),
                "eventType", "Company event",
                "amountOfPersons", "48",
                "city", "Oslo",
                "budget", "10000",
                "context", "none"
        );

        Menu invoke = (Menu) budgetAgent.invoke(eventType);

        System.out.println(invoke);
    }
}
