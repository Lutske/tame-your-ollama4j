package nl.lutske;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.models.chat.OllamaChatMessageRole;
import io.github.ollama4j.models.chat.OllamaChatRequest;
import io.github.ollama4j.models.chat.OllamaChatResult;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocalToolCallingExample {

    // Simple DTO
    record Expense(LocalDate date, String description, double amount) {
    }

    record CategorizedExpense(Expense expense, String category, double confidence) {
    }

    public static void main(String[] args) throws Exception {

        // 1) Connect to Ollama (ollama4j)
        Ollama ollama = new Ollama("http://localhost:11434/");

        String model = "qwen2.5:7b";

        // Input similar to the LangChain4j example
        String input = """
                Categorize these expenses:
                - 2026-02-01 | Train ticket NS | 18.40
                - 2026-02-01 | AWS invoice | 42.12
                - 2026-02-01 | Lunch at cafe | 11.80
                """;

        IO.println(input);

        // 2) Step 1: Ask LLM to normalize the expenses into a strict line format
        //    (This replaces the "tool call" detection LangChain4j would do for you.)
        String extractionPrompt = """
                Extract ALL expenses from the text below.
                Output ONLY lines in this exact format (no bullets, no extra text):
                yyyy-MM-dd|description|amount
                
                Text:
                %s
                """.formatted(input);

        OllamaChatRequest builder = OllamaChatRequest.builder().withModel(model);

        OllamaChatRequest extractRequest = builder
                .withMessage(OllamaChatMessageRole.USER, extractionPrompt)
                .build();

        OllamaChatResult extractResult = ollama.chat(extractRequest, null);
        String normalized = extractResult.getResponseModel().getMessage().getResponse();

        List<Expense> expenses = parseExpenses(normalized);

        // 3) Step 2: "Tool execution" in Java (categorization logic)
        List<CategorizedExpense> categorized = new ArrayList<>();
        for (Expense e : expenses) {
            categorized.add(categorizeExpense(e));
        }

        // 4) Step 3: Feed tool results back to the model and ask for a nice summary
        String toolResults = categorized.stream()
                .map(ce -> "%s | %s | %.2f | %s (%.2f)"
                        .formatted(
                                ce.expense.date(),
                                ce.expense.description(),
                                ce.expense.amount(),
                                ce.category,
                                ce.confidence
                        ))
                .reduce("", (a, b) -> a.isEmpty() ? b : a + "\n" + b);

        String finalPrompt = """
                You are a finance assistant.
                
                Here are categorized expenses (date | description | amount | category (confidence)):
                %s
                
                Give a short summary:
                - list each expense with its category
                - then give 1-2 bullet points explaining the categorization choices
                Keep it concise.
                """.formatted(toolResults);


        // Bonus: STREAM the final answer (generateAsync streams tokens)
        // This is a "generate" call, not "chat with roles", but it streams nicely.
        boolean raw = false;
        OllamaAsyncResultStreamer streamer =
                ollama.generateAsync(model, finalPrompt, raw, ThinkMode.DISABLED);

        int pollIntervalMs = 150;
        while (true) {
            String chunk = streamer.getResponseStream().poll();
            if (chunk != null) System.out.print(chunk);

            Thread.sleep(pollIntervalMs);
            if (!streamer.isAlive()) break;
        }

        System.out.println("\n\n--- done ---");
        // If you want the full final text:
        // System.out.println(streamer.getCompleteResponse());
    }

    private static List<Expense> parseExpenses(String normalizedLines) {
        List<Expense> out = new ArrayList<>();
        if (normalizedLines == null || normalizedLines.isBlank()) return out;

        for (String line : normalizedLines.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;

            // Expect: yyyy-MM-dd|description|amount
            String[] parts = trimmed.split("\\|");
            if (parts.length != 3) continue;

            LocalDate date = LocalDate.parse(parts[0].trim());
            String desc = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());

            out.add(new Expense(date, desc, amount));
        }
        return out;
    }

    private static CategorizedExpense categorizeExpense(Expense e) {
        String d = e.description().toLowerCase(Locale.ROOT);

        String category;
        double confidence;

        if (d.contains("ns") || d.contains("train") || d.contains("uber")) {
            category = "Transport";
            confidence = 0.90;
        } else if (d.contains("ah") || d.contains("aldi") || d.contains("jumbo")) {
            category = "Groceries";
            confidence = 0.90;
        } else if (d.contains("aws") || d.contains("azure") || d.contains("gcp")) {
            category = "Cloud / Software";
            confidence = 0.85;
        } else if (d.contains("lunch") || d.contains("cafe") || d.contains("restaurant")) {
            category = "Food & Drinks";
            confidence = 0.80;
        } else {
            category = "Other";
            confidence = 0.60;
        }

        return new CategorizedExpense(e, category, confidence);
    }
}
