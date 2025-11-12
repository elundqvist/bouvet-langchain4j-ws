package no.bouvet.agentic.ai.aperitif._A3_tools;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import no.bouvet.agentic.ai.common.AiUtils;

/**
 * Fullfør Tools-klassen. Legg til støtte for Tools i AiServices.builder.
 */
public class FunctionCallToolExample {
    public static void main(String[] args) throws Exception {
        ChatModel model = AiUtils.model();

        ChatBot bot = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .tools(new Tools())
                .build();

        String response = bot.chat("What is the current datetime. Use provided tool. Format as norwegian date and time.");
        System.out.println(response);
    }
}
