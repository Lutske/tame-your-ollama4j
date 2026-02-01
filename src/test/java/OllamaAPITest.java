import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;

public class OllamaAPITest {

    public static void main() throws OllamaException {
        Ollama ollama = new Ollama();

        boolean isOllamaServerReachable = ollama.ping();

        IO.println("Is Ollama server running: " + isOllamaServerReachable);
    }
}