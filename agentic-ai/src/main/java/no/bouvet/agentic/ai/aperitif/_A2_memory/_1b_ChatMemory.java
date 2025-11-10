package no.bouvet.agentic.ai.aperitif._A2_memory;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Lag en enkel chat bot som en AI service. Prompt eks.: Hi, tell me an Java joke.
 * Dok: https://docs.langchain4j.dev/tutorials/ai-services#ai-services-1
 */
public class _1b_ChatMemory {
    public static void main(String[] args) {
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logRequests(true)
                .logResponses(true)
                .build();

        ChatBot assistant = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();

        String answer = assistant.chat("Hei, jeg heter Ola Nordmann!");
        System.out.println(answer);

        String answerWithName = assistant.chat("Hva heter jeg?");
        System.out.println(answerWithName);
    }
}
