package no.bouvet.agentic.ai.oppgave.agents;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.model.chat.ChatModel;
import no.bouvet.agentic.ai.common.AiUtils;
import no.bouvet.agentic.ai.oppgave.domain.Menu;
import no.bouvet.agentic.ai.oppgave.domain.Venue;

public class AgentsProvider {
    final ChatModel model;

    public AgentsProvider() {
        this.model = AiUtils.model();
    }

    public VenueSuggesterAgent provideVenueAgent() {
        return AgenticServices.agentBuilder(VenueSuggesterAgent.class)
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
    }

    public MenuSuggesterAgent provideMenuAgent() {
        return AgenticServices.agentBuilder(MenuSuggesterAgent.class)
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
    }

    public BudgetControllerAgent provideControlAgent(McpToolProvider mcpToolProvider) {
        return AgenticServices.agentBuilder(BudgetControllerAgent.class)
                .chatModel(model)
                .toolProvider(mcpToolProvider)
                .outputKey("budgetStatus")
                .description("Budget controller agent")
                .build();
    }

    public EntertainmentSuggesterAgent provideEntertainmentAgent() {
        return AgenticServices.agentBuilder(EntertainmentSuggesterAgent.class)
                .chatModel(model)
                .outputKey("entertainment")
                .afterAgentInvocation(agentResponse -> {
                    AgenticScope agenticScope = agentResponse.agenticScope();
                    if (agentResponse.output() instanceof Menu menu) {
                        agenticScope.writeState("pricePerPerson", menu.pricePerPerson());
                        System.out.printf("Price per person: %s%n", menu.pricePerPerson());
                    }
                })
                .description("Menu suggestion agent")
                .build();
    }

    public ChatModel getModel() {
        return model;
    }
}
