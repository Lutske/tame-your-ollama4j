package nl.lutske.toolCalling;


import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.request.ThinkMode;

public class EierbalToolCalling {

    static void main() throws Exception {

        Ollama ollama = new Ollama("http://localhost:11434");
        ollama.setRequestTimeoutSeconds(120);

        // Register all four Java tools
        ollama.registerAnnotatedTools(new EierbalTools());

        String prompt = """
                I'm organizing a Java meetup for 40 developers.
                Everyone wants 2 eierballen.
                Each eierbal costs 3.50 euros.

                How many eierballen do I need,
                and what is the total budget?
                """;

        OllamaChatRequest request = OllamaChatRequest.builder()
                .withModel("qwen3:4b")
                .withMessage(
                        OllamaChatMessageRole.SYSTEM,
                        "Use the available tools. Reply in one short sentence. "
                                + "Do not explain your calculations."
                )
                .withMessage(OllamaChatMessageRole.USER, prompt)
                .withUseTools(true)
                .withThinking(ThinkMode.DISABLED)
                .build();

        OllamaChatResult result = ollama.chat(request, null);

        System.out.println("\n🤖 LLM RESPONSE:");
        System.out.println(
                result.getResponseModel()
                        .getMessage()
                        .getResponse()
        );
    }
}
