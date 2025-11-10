package no.bouvet.agentic.ai.oppgave.domain;

public record Venue(String name, String venueType, String description, Integer capacity) {

    @Override
    public String toString() {
        return "%s, type %s".formatted(name, venueType);
    }
}
