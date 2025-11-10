package no.bouvet.agentic.ai.aperitif._A1_simple;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Lag en enkel AI agent.
 * Dok: https://docs.langchain4j.dev/tutorials/agents#agents-in-langchain4j
 */
public class SimpleAiAgent {
    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .build();

        PoetAgent poetAgent = AgenticServices.agentBuilder(PoetAgent.class)
                .chatModel(model)
                .outputKey("poetAgent")
                .build();

        String response = poetAgent.writePoem("Birds");
        System.out.println("---- Poem ----");
        System.out.println(response);
    }
}
