import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;

public class OllamaAPIDifferentPortTest {

    static void main() throws OllamaException {
        String host = "http://localhost:11434/";

        Ollama ollama = new Ollama(host);

        boolean isOllamaServerReachable = ollama.ping();

        IO.println("Is Ollama server running: " + isOllamaServerReachable);
    }
}