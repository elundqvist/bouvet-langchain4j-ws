package no.bouvet.agentic.ai.common;

import dev.langchain4j.model.openai.OpenAiChatModel;

public abstract class AiUtils {
    public static final String GPT_4_O_MINI = "gpt-4o-mini";
    public static final String OPENAI_API_KEY = "OPENAI_API_KEY";

    private AiUtils() {
    }

    public static OpenAiChatModel model() {
        return model(false);
    }

    public static OpenAiChatModel modelWithLog() {
        return model(true);
    }

    private static OpenAiChatModel model(boolean log) {
        return OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logResponses(log)
                .logRequests(log)
                .build();
    }
}
