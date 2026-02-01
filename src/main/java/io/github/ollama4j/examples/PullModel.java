package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;

public class PullModel {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";
        String model = "lfm2.5-thinking";

        Ollama ollamaAPI = new Ollama(host);

        ollamaAPI.pullModel(model);

        IO.println("Done pulling model " + model);
    }
}