package no.bouvet.quarkus.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkiverse.mcp.server.ToolResponse;

public class McpTools {
    @Tool(description = "Adds two numbers and returns their sum.")
    ToolResponse sum(
            @ToolArg(description = "The first integer to add.", defaultValue = "0") int first,
            @ToolArg(description = "The second integer to add.", defaultValue = "0") int second
    ) {
        int sum = first + second;
        return ToolResponse.success(String.valueOf(sum));
    }

    @Tool(description = "Calculates the total cost of an event")
    ToolResponse budgetCalculator(
            @ToolArg(description = "amuntOfPersons", defaultValue = "0") int amountOfPersons,
            @ToolArg(description = "pricePerPersons", defaultValue = "0") int pricePerPersons
    ) {
        int sum = amountOfPersons * pricePerPersons;
        return ToolResponse.success(String.valueOf(sum));
    }
}
