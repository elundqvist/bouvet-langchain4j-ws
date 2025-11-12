package no.bouvet.agentic.ai.oppgave;

import no.bouvet.agentic.ai.oppgave.agents.AgentsProvider;
import no.bouvet.agentic.ai.oppgave.mcp.McpProvider;

/**
 * De 4 ulike workflows som du nå har utviklet kan kombineres som du selv ønsker.
 *
 * Sett opp en composed workflow basert på de 4 foregående oppgavene.
 */
public class _O5_ComposedWorkflow {
    public static void main(String[] args) {
        AgentsProvider provider = new AgentsProvider();
        McpProvider mcpProvider = new McpProvider();
        var venueAgent = provider.provideVenueAgent();
        var menuAgent = provider.provideMenuAgent();
        var controlAgent = provider.provideControlAgent(mcpProvider.mcp());
        var entertainmentAgent = provider.provideEntertainmentAgent();
    }
}
