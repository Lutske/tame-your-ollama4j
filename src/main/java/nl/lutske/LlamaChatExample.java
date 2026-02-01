package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatStreamObserver;

public class LlamaChatExample {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";

        // connect to the local Ollama instance
        Ollama ollama = new Ollama(host);

        // use your custom model name instead of a built-in type
        String model = "llama-animal-expert";

        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

        // Define a stream observer.
        OllamaChatStreamObserver streamObserver = new OllamaChatStreamObserver();

        // If thinking tokens are found, print them in lowercase :)
        streamObserver.setThinkingStreamHandler(
                message -> IO.print(message.toUpperCase()));

        // Response tokens to be printed in lowercase
        streamObserver.setResponseStreamHandler(
                message -> IO.print(message.toLowerCase()));

        askAndPrint(ollama, model, "How do we call a baby llama?", streamObserver);
        askAndPrint(ollama, model, "Are llamas social animals?", streamObserver);
    }

    private static void askAndPrint(Ollama ollama,
                                    String model,
                                    String question,
                                    OllamaChatStreamObserver observer)
            throws OllamaException {

        // Print the question BEFORE the model starts answering
        IO.println("\nQuestion: " + question);

        OllamaChatRequest request = OllamaChatRequest.builder()
                .withModel(model)
                .withMessage(OllamaChatMessageRole.USER, question)
                .build();

        // Trigger streaming answer
        ollama.chat(request, observer);

        IO.println("\n");
    }
}
