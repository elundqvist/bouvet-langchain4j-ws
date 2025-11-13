package no.bouvet.agentic.ai.aperitif._A3_tools;

import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tools {

    // 1. Anngi Tools annotasjon for å markere metoden som en tool.
    @Tool("Returns the current date and time in ISO format")
    public String getCurrentDateTime(){
        // 2. returner dagens tid og dato i ISO_DATE_TIME.
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
