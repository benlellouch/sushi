package comp1206.sushi.Tabs;

import comp1206.sushi.common.Ingredient;

public class Recipe {
    private Ingredient ingredient;
    private Number amount;

    public Recipe(Ingredient ingredient, Number amount){
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Number getAmount() {
        return amount;
    }
}
