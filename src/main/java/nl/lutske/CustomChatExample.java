package nl.lutske;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatRequestBuilder;
import io.github.ollama4j.models.chat.OllamaChatResult;

public class CustomChatExample {

    public static void main(String[] args) throws Exception {
        String host = "http://localhost:11434/";

        // connect to the local Ollama instance
        OllamaAPI ollamaAPI = new OllamaAPI(host);
        ollamaAPI.setVerbose(false);

        // use your custom model name instead of a built-in type
        OllamaChatRequestBuilder builder = OllamaChatRequestBuilder
                .getInstance("j-on-the-beach-example");

        // create first user question about llamas
        OllamaChatRequest requestModel = builder
                .withMessage(OllamaChatMessageRole.USER, "What are the best known brands for blue minature trains?")
                .build();

        System.out.println("First question: " + requestModel.getMessages().getFirst().getContent());

        // ask the model
        OllamaChatResult chatResult = ollamaAPI.chat(requestModel);

        System.out.println("First answer: " + chatResult.getResponseModel().getMessage().getContent());

        // ask a follow-up question in the same conversation
        requestModel = builder
                .withMessages(chatResult.getChatHistory())
                .withMessage(OllamaChatMessageRole.USER, "What is the best brand in your opinion?")
                .build();

        System.out.println("Second question: " + requestModel.getMessages().getFirst().getContent());

        chatResult = ollamaAPI.chat(requestModel);

        System.out.println("Second answer: " + chatResult.getResponseModel().getMessage().getContent());
    }
}
