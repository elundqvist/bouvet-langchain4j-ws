package no.bouvet.agentic.ai.aperitif._A3_tools;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

public class FunctionCallToolExample {
    public static void main(String[] args) throws Exception {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logRequests(true)
                .logResponses(true)
                .build();

        ChatBot bot = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .tools(new Tools())
                .build();

        String response = bot.chat("What is the current datetime. Use provided tool. Format as norwegian date and time.");
        System.out.println(response);
    }
}
