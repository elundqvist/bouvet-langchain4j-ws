package no.bouvet.agentic.ai.oppgave.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport;

import java.time.Duration;
import java.util.List;

public class McpProvider {
    private final McpClient mcpClient;

    public McpProvider() {
        StreamableHttpMcpTransport transport = new StreamableHttpMcpTransport.Builder()
                .url("http://localhost:8080/mcp")
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();
    }

    public McpToolProvider mcp() {
        mcpClient.checkHealth();

        return McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();
    }

    void cleanUp() throws Exception {
        mcpClient.close();
    }
}
