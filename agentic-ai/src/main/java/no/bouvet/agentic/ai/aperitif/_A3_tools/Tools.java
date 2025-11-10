package no.bouvet.agentic.ai.aperitif._A3_tools;

import dev.langchain4j.agent.tool.Tool;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Tools {

    @Tool("Fetch current date and time")
    public String getCurrentDateTime(){
        return OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
