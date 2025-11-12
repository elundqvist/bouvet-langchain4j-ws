package no.bouvet.agentic.ai.aperitif._A2_memory;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Implementer støtte for Chat Memory slik at OpenAI husker hva du heter.
 */
public class _1b_ChatMemory {
    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logRequests(true)
                .logResponses(true)
                .build();

        ChatBot assistant = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .build();

        String answer = assistant.chat("Hei, jeg heter ...!");
        System.out.println(answer);

        String answerWithName = assistant.chat("Hva heter jeg?");
        System.out.println(answerWithName);
    }
}
