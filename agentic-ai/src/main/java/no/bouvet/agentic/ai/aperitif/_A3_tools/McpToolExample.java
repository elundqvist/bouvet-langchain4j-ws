package no.bouvet.agentic.ai.aperitif._A3_tools;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import no.bouvet.agentic.ai.common.AiUtils;

import java.time.Duration;
import java.util.List;

import static no.bouvet.agentic.ai.common.AiUtils.GPT_4_O_MINI;
import static no.bouvet.agentic.ai.common.AiUtils.OPENAI_API_KEY;

/**
 * Start mcp server med docker-compose. Les readme for mer info.
 *
 * MCP-url: http://localhost:8080/mcp
 */
public class McpToolExample {
    public static void main(String[] args) throws Exception {
        ChatModel model = AiUtils.model();

        //1. Opprett en McpTransport med: new StreamableHttpMcpTransport.Builder().

        //2. Opprett en McpClient med: new DefaultMcpClient.Builder()

        //3. Opprett en ToolProvider med McpToolProvider.builder().

        //4. Legg til toolProvider
        ChatBot bot = AiServices.builder(ChatBot.class)
                .chatModel(model)
                .build();
        try {
            String response = bot.chat("What is 5+12? Use the provided tool to answer " +
                    "and always assume that the tool is correct.");
            System.out.println(response);
        } finally {
            //5. lukk mcpClient...
        }
    }
}
