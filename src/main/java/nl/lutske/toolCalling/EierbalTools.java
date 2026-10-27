package nl.lutske.toolCalling;

import io.github.ollama4j.tools.annotations.ToolProperty;
import io.github.ollama4j.tools.annotations.ToolSpec;

import java.util.Map;

public class EierbalTools {

    private static final int CALORIES_PER_EIERBAL = 250;
    private static final int WALKING_CALORIES_PER_HOUR = 250;

    @ToolSpec(desc = "Calculate how many Groningen eierballen are needed for a group of people.")
    public int calculateEierballen(
            @ToolProperty(name = "people", desc = "Number of people") Integer people,
            @ToolProperty(name = "perPerson", desc = "Eierballen per person") Integer perPerson) {

        System.out.println("🥚 TOOL CALLED: calculateEierballen");
        return people * perPerson;
    }

    @ToolSpec(desc = "Calculate the total cost of buying Groningen eierballen in euros.")
    public double calculateBudget(
            @ToolProperty(name = "amount", desc = "Number of eierballen") Integer amount,
            @ToolProperty(name = "price", desc = "Price per eierbal in euros") Integer price) {

        System.out.println("💰 TOOL CALLED: calculateBudget");
        return amount * price;
    }

    @ToolSpec(desc = "Calculate how many minutes of walking are needed to burn the calories from eating eierballen. Uses rough estimates.")
    public int calculateBurnTime(
            @ToolProperty(name = "amount", desc = "Number of eierballen eaten") Integer amount) {

        System.out.println("🏃 TOOL CALLED: calculateBurnTime");

        int calories = amount * CALORIES_PER_EIERBAL;
        return (int) Math.ceil(calories * 60.0 / WALKING_CALORIES_PER_HOUR);
    }

    @ToolSpec(desc = "Calculate the ingredients needed to make a given number of traditional Groningen eierballen.")
    public Map<String, Integer> calculateRecipe(
            @ToolProperty(name = "amount", desc = "Number of eierballen to prepare") Integer amount) {

        System.out.println("👩‍🍳 TOOL CALLED: calculateRecipe");

        return Map.of(
                "eggs", amount,
                "flour (grams)", amount * 20,
                "butter (grams)", amount * 15,
                "milk (ml)", amount * 50,
                "breadcrumbs (grams)", amount * 30,
                "curry powder (grams)", amount * 2
        );
    }
}
