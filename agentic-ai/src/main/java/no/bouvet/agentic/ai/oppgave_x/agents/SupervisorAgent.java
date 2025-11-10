package no.bouvet.agentic.ai.oppgave_x.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface SupervisorAgent {
    @Agent
    String invoke(@V("request") String request,
                  @V("budgetAdjustment") String budgetAdjustment,
                  @V("previousTotalPrice") String previousTotalPrice,
                  @V("supervisorContext") String supervisorContext);
}
