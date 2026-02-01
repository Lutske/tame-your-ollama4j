package io.github.ollama4j.examples;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.response.Model;

import java.util.List;

public class ListLibraryModels {
    static void main() throws OllamaException {

        String host = "http://localhost:11434/";

        Ollama ollama = new Ollama(host);

        List<Model> libraryModels = ollama.listModels();

        libraryModels.forEach(System.out::println);
    }
}