package no.bouvet.agentic.ai.aperitif._A1_simple_agent;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import no.bouvet.agentic.ai.common.AiUtils;

/**
 * Fullfør PoetAgent, main her er komplett.
 */
public class SimpleAiAgent {
    public static void main(String[] args) {
        ChatModel model = AiUtils.model();

        PoetAgent poetAgent = AgenticServices.agentBuilder(PoetAgent.class)
                .chatModel(model)
                .outputKey("poetAgent")
                .build();

        String response = poetAgent.writePoem("Birds");
        System.out.println("---- Poem ----");
        System.out.println(response);
    }
}
