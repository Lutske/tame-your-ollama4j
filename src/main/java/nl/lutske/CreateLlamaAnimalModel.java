package nl.lutske;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.request.CustomModelRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateLlamaAnimalModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {

        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.createModel(
                CustomModelRequest.builder()
                        .model("llama-animal-expert")
                        .from("qwen2.5:7b")
                        .system("You are a zoologist who specializes in llamas. You know everything about their behavior, habitat, care, and history. Answer all questions as a friendly and knowledgeable llama expert.")
                        .build());
    }
}