
import io.github.ollama4j.OllamaAPI;

public class OllamaAPIDifferentPortTest {

    public static void main(String[] args) {
        String host = "http://localhost:11434/";

        OllamaAPI ollamaAPI = new OllamaAPI(host);

        ollamaAPI.setVerbose(true);

        boolean isOllamaServerReachable = ollamaAPI.ping();

        System.out.println("Is Ollama server running: " + isOllamaServerReachable);
    }
}