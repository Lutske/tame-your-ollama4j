package nl.lutske.toolCalling;

import io.github.ollama4j.tools.annotations.ToolProperty;
import io.github.ollama4j.tools.annotations.ToolSpec;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EierbalTools {

    private static final int CALORIES_PER_EIERBAL = 250;
    private static final int WALKING_CALORIES_PER_HOUR = 250;

    @ToolSpec(desc = "Calculate the number of Groningen eierballen needed for a group.")
    public Integer calculateEierballen(
            @ToolProperty(name = "people", desc = "Number of people") Integer people,
            @ToolProperty(name = "perPerson", desc = "Eierballen per person") Integer perPerson) {

        System.out.println("🥚 TOOL CALLED: calculateEierballen");
        return Math.multiplyExact(people, perPerson);
    }

    @ToolSpec(desc = "Calculate the total price of eierballen in euros.")
    public BigDecimal calculateEierballenBudget(
            @ToolProperty(name = "amount", desc = "Number of eierballen") Integer amount,
            @ToolProperty(name = "price", desc = "Price per eierbal in euros") BigDecimal price) {

        System.out.println("💰 TOOL CALLED: calculateEierballenBudget");

        return price.multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    @ToolSpec(desc = "Estimate how many minutes of walking are needed to burn the calories from eierballen. Assumes 250 calories per eierbal and 250 calories burned per hour.")
    public Integer calculateEierballenBurnTime(
            @ToolProperty(name = "amount", desc = "Number of eierballen eaten") Integer amount) {

        System.out.println("🏃 TOOL CALLED: calculateEierballenBurnTime");

        int calories = amount * CALORIES_PER_EIERBAL;
        return (int) Math.ceil(
                calories * 60.0 / WALKING_CALORIES_PER_HOUR
        );
    }

    @ToolSpec(desc = "Calculate ingredient quantities to prepare a given number of Groningen eierballen using a simplified demo recipe.")
    public String calculateEierballenRecipe(
            @ToolProperty(name = "amount", desc = "Number of eierballen") Integer amount) {

        System.out.println("👩‍🍳 TOOL CALLED: calculateEierballenRecipe");

        return """
                Ingredients for %d eierballen:
                Eggs: %d
                Flour: %d grams
                Butter: %d grams
                Milk: %d ml
                Breadcrumbs: %d grams
                Curry powder: %d grams
                """.formatted(
                amount,
                amount,
                amount * 20,
                amount * 15,
                amount * 50,
                amount * 30,
                amount * 2
        );
    }
}
