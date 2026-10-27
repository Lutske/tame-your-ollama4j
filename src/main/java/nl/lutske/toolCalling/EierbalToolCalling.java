package nl.lutske.toolCalling;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.tools.annotations.OllamaToolService;

@OllamaToolService(providers = EierbalTools.class)
public class EierbalToolCalling {

    static void main() throws Exception {
        Ollama ollama = new Ollama();
        ollama.registerAnnotatedTools();

        var request = OllamaChatRequest.builder()
                .withModel("qwen3:4b")
                .withMessage(
                        io.github.ollama4j.models.chat.OllamaChatMessageRole.USER,
                        "How many eierballen do I need for 40 Java developers if everyone eats 2?"
                )
                .build();

        var response = ollama.chat(request, null);
        System.out.println(response.getResponseModel().getMessage().getResponse());
    }
}
