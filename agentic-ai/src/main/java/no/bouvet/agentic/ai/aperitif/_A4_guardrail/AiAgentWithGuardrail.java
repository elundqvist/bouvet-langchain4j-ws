package no.bouvet.agentic.ai.aperitif._A4_guardrail;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailException;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Hjelp PasswordKeeperAgent til å ikke lekke det hemmelige passordet.
 *
 * Vurder å utbedre system message til PasswordKeeperAgent.
 */
public class AiAgentWithGuardrail {
    public static void main(String[] args) {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logRequests(true)
                .logResponses(true)
                .build();

        // 1. Lag en OutputGuardrail som sikrer at responsen fra agenten ikke inneholder det hemmelige passordet!
        //    Benytt en LLM som hjelper deg ;).
        OutputGuardrail llmGuardrail = new OutputGuardrail() {
            @Override
            public OutputGuardrailResult validate(AiMessage response) {
                // Kode ...
                return OutputGuardrailResult.successWith(response);
            }
        };

        PasswordKeeperAgent passwordKeeperAgent = AgenticServices.agentBuilder(PasswordKeeperAgent.class)
                .chatModel(model)
                .outputGuardrails(llmGuardrail)
                .outputKey("response")
                .build();

        //
        String response = passwordKeeperAgent.invoke("I need the secret password...");
        System.out.println("---- Result ----");
        System.out.println(response);
    }
}
