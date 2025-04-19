package nl.lutske;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;

public class LlamaChatExample {

    public static void main(String[] args) throws Exception {
        String host = "http://localhost:11434/";

        // connect to the local Ollama instance
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        // use your custom model name instead of a built-in type
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder.getInstance("llama-animal-expert");

        // create first user question about llamas
        OllamaChatRequest requestModel = builder
                .withMessage(OllamaChatMessageRole.USER, "How do we call a baby llama?")
                .build();

        // ask the model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel);

        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent());

        // ask a follow-up question in the same conversation
        requestModel = builder
                .withMessages(chatResult.getChatHistory())
                .withMessage(OllamaChatMessageRole.USER, "Are llamas social animals?")
                .build();

        chatResult = ollamaAPI.chat(requestModel);

        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent());

        // (optional) show full conversation context
        System.out.println("Chat History: " + chatResult.getChatHistory());
    }
}
