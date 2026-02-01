package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.response.ModelDetail;

public class GetModelDetails {

    static void main() throws OllamaException {

        String host = "http://localhost:11434/";

        Ollama ollama = new Ollama(host);

        ModelDetail modelDetails = ollama.getModelDetails("qwen2.5:7b");

        IO.println(modelDetails);
    }
}