package nl.lutske;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.request.CustomModelRequest;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateCustomModel {
    public static void main(String[] args) throws IOException, OllamaBaseException, URISyntaxException, InterruptedException {

        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.createModel(
                CustomModelRequest.builder()
                        .model("j-on-the-beach-example")
                        .from("qwen2.5:7b")
                        .system("You are a minature train expert who specializes in blue miniature trains. " +
                                "You know everything about them and history. " +
                                "Answer all questions as a aggresive and knowledgeable minature train expert.")
                        .build());
    }
}