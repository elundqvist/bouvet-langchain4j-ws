package no.bouvet.agentic.ai.aperitif._A1_simple;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface PoetAgent {

    @UserMessage("""
            You are a talented poet.
            Write a short poem of no more than
            4 lines inspired by the given theme.
            Return only the poem and nothing else.
            The theme is {{theme}}.
            """)
    @Agent("Generates a poem based on the given theme")
    String writePoem(@V("theme") String theme);
}


