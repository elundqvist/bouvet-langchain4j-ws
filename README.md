# 🤖 Agentic AI Workshop med LangChain4J

En praktisk workshop for å lære om agentic AI-konsepter ved bruk av LangChain4J-rammeverket. Dette prosjektet inneholder eksempler og øvelser som dekker grunnleggende til avanserte temaer innen AI-agenter.

## 📋 Innholdsfortegnelse

- [Om Prosjektet](#om-prosjektet)
- [Forutsetninger](#forutsetninger)
- [Komme i gang](#komme-i-gang)
- [Prosjektstruktur](#prosjektstruktur)
- [Workshop-moduler](#workshop-moduler)
- [Oppgaver](#oppgaver)
- [Kjøring av Eksempler](#kjøring-av-eksempler)
- [Teknologier](#teknologier)

## Om Prosjektet

Dette er en workshop designet for å gi praktisk erfaring med utvikling av AI-agenter ved bruk av LangChain4J. Prosjektet er strukturert i to hovedmoduler:

- **agentic-ai**: Hovedmodulen med aperitif-eksempler og oppgaver
- **quarkus-mcp-server**: En Quarkus-basert MCP (Model Context Protocol) server for verktøyintegrasjon

## 🔧 Forutsetninger

- **Java 21** eller nyere
- **Maven 3.9+**
- **Docker** (for MCP-serveren)
- **OpenAI API-nøkkel**

## 🚀 Komme i gang

### 1. Klon repositoriet

```bash
git clone https://github.com/astil/bouvet-langchain4j-ws.git
cd bouvet-langchain4j-ws
```

### 2. Sett opp OpenAI API-nøkkel

Sett miljøvariabelen `OPENAI_API_KEY`:

```bash
export OPENAI_API_KEY="din-api-nøkkel-her"
```

### 3. Bygg prosjektet

```bash
mvn clean install
```

### 4. Start MCP-serveren (valgfritt, for verktøyeksempler)

```bash
docker-compose up -d
```

MCP-serveren vil være tilgjengelig på `http://localhost:8080`

## 📁 Prosjektstruktur

```
bouvet-langchain4j-ws/
├── agentic-ai/                    # Hovedmodul med eksempler og oppgaver
│   └── src/main/java/no/bouvet/agentic/ai/
│       ├── aperitif/              # Introduksjonseksempler
│       │   ├── _A1_simple/        # Enkle AI-agenter
│       │   ├── _A2_memory/        # Chat-minne og kontekst
│       │   ├── _A3_tools/         # Verktøyintegrasjon
│       │   └── _A4_guardrail/     # Sikkerhet og guardrails
│       ├── oppgave/               # Workshop-oppgaver
│       └── common/                # Felles hjelpeklasser
├── quarkus-mcp-server/            # MCP-server for verktøy
└── docker-compose.yml             # Docker-konfigurasjon
```

## 📚 Workshop-moduler

### Aperitif (Introduksjonseksempler)

#### A1: Simple AI Agents
- **SimpleAiAgent**: Grunnleggende AI-agent implementasjon
- **PoetAgent**: Spesialisert agent for å skrive dikt

Lær hvordan du:
- Oppretter en enkel AI-agent
- Konfigurerer ChatModel med OpenAI
- Bruker AgenticServices for å bygge agenter

#### A2: Memory (Minne)
- **SimpleChatBot**: Enkel chatbot uten minne
- **ChatMemory**: Demonstrasjon av chat-minne
- **ChatBot**: Chatbot med persistent kontekst

Lær hvordan du:
- Implementerer chat-minne for kontekstuell forståelse
- Håndterer samtalehistorikk
- Bygger chatboter som husker tidligere interaksjoner

#### A3: Tools (Verktøy)
- **FunctionCallToolExample**: Bruk av funksjonsverktøy
- **McpToolExample**: Integrasjon med MCP-serveren
- **Tools**: Verktøydefinisjoner

Lær hvordan du:
- Integrerer eksterne verktøy med AI-agenter
- Bruker funksjonsanrop for å utvide agent-kapasiteter
- Kobler til MCP-serveren for avanserte verktøy

#### A4: Guardrails (Sikkerhet)
- **AiAgentWithGuardrail**: Agent med sikkerhetsbegrensninger
- **PasswordKeeperAgent**: Sikker håndtering av sensitive data

Lær hvordan du:
- Implementerer guardrails for å beskytte sensitive data
- Kontrollerer agent-atferd
- Sikrer trygg AI-bruk

## 🎯 Oppgaver

Workshopen inneholder 7 praktiske oppgaver for å mestre ulike workflow-mønstre:

1. **O1_SequenceWorkflow**: Sekvensiell behandling av oppgaver
2. **O2_ParallelWorkflow**: Parallell eksekvering av oppgaver
3. **O3_LoopWorkflow**: Iterative workflows
4. **O4_ConditionalWorkflow**: Betinget logikk i workflows
5. **O5_ComposedWorkflow**: Sammensatte workflows
6. **O6_SupervisorAgent**: Supervisor-agent mønster
7. **O7_HumanInTheLoop**: Menneskelig interaksjon i AI-workflows

## ▶️ Kjøring av Eksempler

### Kjør et aperitif-eksempel

```bash
cd agentic-ai
mvn exec:java -Dexec.mainClass="no.bouvet.agentic.ai.aperitif._A1_simple_agent.SimpleAiAgent"
```

### Kjør en oppgave

```bash
mvn exec:java -Dexec.mainClass="no.bouvet.agentic.ai.oppgave._O1_SequenceWorkflow"
```

### Tips for utvikling

- Alle eksempler har `main`-metoder som kan kjøres direkte fra din IDE
- Logg er konfigurert for å vise AI-forespørsler og -svar
- Bruk `AiUtils`-klassen for felles konfigurasjon

## 🛠 Teknologier

- **LangChain4J 1.8.0**: Rammeverk for AI-agenter
- **OpenAI GPT-4o-mini**: Language model
- **Quarkus 3.29.0**: For MCP-serveren
- **Model Context Protocol (MCP)**: For verktøyintegrasjon
- **Java 21**: Programmeringsspråk
- **Maven**: Byggverktøy
- **Docker**: Containerisering av MCP-serveren

## 📖 Ressurser

- [LangChain4J Dokumentasjon](https://docs.langchain4j.dev/)
- [OpenAI API Dokumentasjon](https://platform.openai.com/docs)
- [Model Context Protocol](https://modelcontextprotocol.io/)
- [Quarkus Guides](https://quarkus.io/guides/)

## 🤝 Bidrag

Dette er et workshop-prosjekt for læring. Eksperimenter gjerne med koden og utvid eksemplene!

## 📝 Lisens

Dette prosjektet er laget for Bouvet workshop-formål.

---

**Lykke til med workshopen! 🚀**
