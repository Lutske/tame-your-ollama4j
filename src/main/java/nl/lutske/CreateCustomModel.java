package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.request.CustomModelRequest;

public class CreateCustomModel {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";
        Ollama ollama = new Ollama(host);

        String modelName = "demo-example";

        ollama.createModel(
                CustomModelRequest.builder()
                        .model(modelName)
                        .from("qwen2.5:7b")
                        .system("You are a Star Wars expert. " +
                                "You know everything about Star Wars and it's history and specialize in the empire. " +
                                "Answer all questions in chewbacca style.")
                        .build());

        IO.println("Done creating " + modelName);
    }
}