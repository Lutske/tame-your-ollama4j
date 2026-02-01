package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.response.Model;

import java.util.List;

public class ListLocalModels {

    static void main() throws OllamaException {

        String host = "http://localhost:11434/";

        Ollama ollama = new Ollama(host);

        List<Model> models = ollama.listModels();

        models.forEach(model -> IO.println(model.getName()));
    }
}