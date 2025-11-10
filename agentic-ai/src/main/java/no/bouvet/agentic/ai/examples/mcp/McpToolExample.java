package no.bouvet.agentic.ai.examples.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;

import java.time.Duration;
import java.util.List;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

public class McpToolExample {
    public static void main(String[] args) throws Exception {
        ChatModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv(OPENAI_API_KEY))
                .modelName(GPT_4_O_MINI)
                .logRequests(true)
                .logResponses(true)
                .build();

        McpTransport transport = new StreamableHttpMcpTransport.Builder()
                .url("http://localhost:8080/mcp")
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        ChatBot bot = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .toolProvider(toolProvider)
                .build();
        try {
            String response = bot.chat("What is 5+12? Use the provided tool to answer " +
                    "and always assume that the tool is correct.");
            System.out.println(response);
        } finally {
            mcpClient.close();
        }
    }
}
