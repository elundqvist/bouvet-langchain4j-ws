package no.bouvet.agentic.ai.aperitif._A0_ai_service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Opprett en enkel chat bot ved bruk av AiService AiServices.create(ChatBot.class, model)
 */
public class _1a_SimpleChatBot {
    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .build();

        //1. AiServices.create(ChatBot.class, model)
        ChatBot chatBot = AiServices.create(ChatBot.class, model);
        
        //2. Kall metoden chat på ChatBot interfacet med en valgfri prompt.
        String response = chatBot.chat("Hei! Kan du fortelle meg om LangChain4J?");
        
        //3. Skriv ut resultatet.
        System.out.println(response);
    }
}
