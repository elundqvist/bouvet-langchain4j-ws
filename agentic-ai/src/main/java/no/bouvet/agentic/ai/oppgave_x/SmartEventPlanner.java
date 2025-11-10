package no.bouvet.agentic.ai.oppgave_x;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.agentic.supervisor.SupervisorAgent;
import dev.langchain4j.agentic.supervisor.SupervisorContextStrategy;
import dev.langchain4j.agentic.supervisor.SupervisorResponseStrategy;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import no.bouvet.agentic.ai.oppgave.domain.Entertainment;
import no.bouvet.agentic.ai.oppgave.domain.EventPlannerResult;
import no.bouvet.agentic.ai.oppgave.agents.BudgetControllerAgent;
import no.bouvet.agentic.ai.oppgave.agents.MenuSuggesterAgent;
import no.bouvet.agentic.ai.oppgave.agents.VenueSuggesterAgent;
import no.bouvet.agentic.ai.oppgave.domain.BudgetStatus;
import no.bouvet.agentic.ai.oppgave.domain.Menu;
import no.bouvet.agentic.ai.oppgave.domain.Venue;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

public class SmartEventPlanner {
    public static void main(String[] args) {
        McpToolProvider mcpToolProvider = null;
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                //.logResponses(true)
                //.logRequests(true)
                .build();

        var venueAgent = AgenticServices.agentBuilder(VenueSuggesterAgent.class)
                .chatModel(model)
                .outputKey("venue")
                .afterAgentInvocation(agentResponse -> {
                    AgenticScope agenticScope = agentResponse.agenticScope();
                    if (agentResponse.output() instanceof Venue venue) {
                        agenticScope.writeState("venueName", venue.name());
                        agenticScope.writeState("venueType", venue.venueType());
                    }
                })
                .description("Venue suggestion agent")
                .build();

        var menuAgent = AgenticServices.agentBuilder(MenuSuggesterAgent.class)
                .chatModel(model)
                .outputKey("menu")
                .afterAgentInvocation(agentResponse -> {
                    AgenticScope agenticScope = agentResponse.agenticScope();
                    if (agentResponse.output() instanceof Menu menu) {
                        agenticScope.writeState("pricePerPerson", menu.pricePerPerson());
                        System.out.printf("Price per person: %s%n", menu.pricePerPerson());
                    }
                })
                .description("Menu suggestion agent")
                .build();

        var controlAgent = AgenticServices.agentBuilder(BudgetControllerAgent.class)
                .chatModel(model)
                .toolProvider(mcpToolProvider)
                .outputKey("budgetStatus")
                .description("Budget controller agent")
                .build();

        UntypedAgent budgetAgent = AgenticServices.loopBuilder()
                .subAgents(menuAgent, controlAgent)
                .outputKey("budgetStatus")
                .exitCondition(agenticScope -> {
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");
                    String budgetAdjustment = budgetStatus != null && budgetStatus.budgetOk() ? "Ok" : "Reduce";
                    String previousTotalPrice = budgetStatus != null ? budgetStatus.totalPrice() : "UNKNOWN";
                    agenticScope.writeState("budgetAdjustment", budgetAdjustment);
                    agenticScope.writeState("previousTotalPrice", previousTotalPrice);

                    return budgetStatus != null && budgetStatus.budgetOk();
                })
                .description("Fetch menu and control budget agents")
                .maxIterations(2)
                .build();

        UntypedAgent agentSeq = AgenticServices.sequenceBuilder()
                .subAgents(venueAgent, budgetAgent)
                .outputKey("event")
                .output(agenticScope -> {
                    Venue venue = (Venue) agenticScope.readState("venue");
                    Menu menu = (Menu) agenticScope.readState("menu");
                    Entertainment entertainment = (Entertainment) agenticScope.readState("entertainment");
                    BudgetStatus budgetStatus = (BudgetStatus) agenticScope.readState("budgetStatus");

                    return new EventPlannerResult(venue, menu, entertainment, budgetStatus);
                })
                .build();

        SupervisorAgent planningSupervisor = AgenticServices.supervisorBuilder(SupervisorAgent.class)
                .chatModel(model)
                .subAgents(venueAgent, budgetAgent)
                .contextGenerationStrategy(SupervisorContextStrategy.CHAT_MEMORY_AND_SUMMARIZATION)
                .responseStrategy(SupervisorResponseStrategy.SUMMARY) // we want a summary of what happened, rather than retrieving a response
                .supervisorContext("Always use the full panel of available agents. Always answer in English.") // optional context for the supervisor on how to behave
                .description("The event planning supervisor")
                .build();

//        Map<String, Object> eventType = Map.of(
//                "eventType", "Company event",
//                "amountOfPersons", "48",
//                "city", "Oslo",
//                "budget", "20000",
//                "budgetAdjustment", "UNKNOWN",
//                "previousTotalPrice", "UNKNOWN"
//        );

        String invoke = planningSupervisor.invoke("""
                Plan an company event in Oslo for 48 persons, the budget is 20000 NOK.
                """);

//        EventPlannerResult resultWithAgenticScope = (EventPlannerResult) agentSeq.invoke(eventType);
        System.out.println(invoke);
    }
}
