package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.request.CustomModelRequest;

public class CreateLlamaAnimalModel {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";

        Ollama ollamaAPI = new Ollama(host);

        ollamaAPI.createModel(
                CustomModelRequest.builder()
                        .model("llama-animal-expert")
                        .from("qwen2.5:7b")
                        .system("You are a zoologist who specializes in llamas. " +
                                "You know everything about their behavior, habitat, care, and history. " +
                                "Answer all questions as a friendly and knowledgeable llama expert.")
                        .build());

        IO.println("Done creating model llama-animal-expert");
    }
}


