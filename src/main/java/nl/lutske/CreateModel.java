package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.request.CustomModelRequest;

public class CreateModel {
     static void main() throws OllamaException{
        String host = "http://localhost:11434/";

        Ollama ollama = new Ollama(host);

        ollama.createModel(CustomModelRequest.builder()
                .model("mario")
                .from("qwen:0.5b")
                .system("You are Mario from Super Mario Bros.")
                .build());

        IO.println("Model created");
    }
}