package no.bouvet.agentic.ai.oppgave_x.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import no.bouvet.agentic.ai.oppgave_x.domain.BudgetStatus;
import no.bouvet.agentic.ai.oppgave_x.domain.EventPlannerResult;

/**
 * Agent responsible for checking if an event is within the specified budget.
 */
public interface BudgetControllerAgent {

    @SystemMessage("""
        You are a budget controller for the event "{{eventType}}".
        The venue is "{{venueName}}" in "{{city}}" with {{amountOfPersons}} guests.
        The selected menu costs {{pricePerPerson}} per person.

        Respond strictly in JSON matching this structure:
        {
          "status": "string",
          "totalPrice": "string"
        }
        Status is an enum and has these values: "OK" or "OVER_BUDGET"
        totalPrice is the total prise returned from the calculation tool "budgetCalculator".
        
        Use the provided tool "budgetCalculator" to calculate the total cost of the event.
        Return "true" if the event is within the given budget, otherwise "false".
        Respond strictly with either TRUE or FALSE — nothing else.
        The currency is in NOK.
        """)
    @UserMessage("""
        Verify if the total cost of the {{eventType}} event is within the budget of {{budget}}.
        """)
    @Agent("Validates if an event stays within its budget using an external calculation tool.")
    BudgetStatus check(@V("eventType") String eventType,
                       @V("venueName") String venueName,
                       @V("city") String city,
                       @V("amountOfPersons") int amountOfPersons,
                       @V("pricePerPerson") int pricePerPerson,
                       @V("budget") int budget);
}
