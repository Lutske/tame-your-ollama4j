package nl.lutske.custom;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.request.CustomModelRequest;

import java.time.LocalDateTime;

public class CreateCustomModel {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";
        Ollama ollama = new Ollama(host);

        String modelName = "nijmegenJUG-example-2";

        IO.println( "[" + LocalDateTime.now() + "] Start creating " + modelName);

        ollama.createModel(
                CustomModelRequest.builder()
                        .model(modelName)
                        .from("qwen2.5:7b")
                        .system("Jij bent een mango expert. " +
                                "Je weet alles over mango's en de geschiedenis daarvan. En je bent specalist in het telen en groeien van mango's. " +
                                "Antwoord al je antwoorden in de Johan Cruijff stijl.")
                        .build());

        IO.println( "[" + LocalDateTime.now() + "] Done creating " + modelName);
    }
}