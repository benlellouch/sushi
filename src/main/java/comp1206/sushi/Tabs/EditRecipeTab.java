package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class EditRecipeTab extends MainTab{

    private TableView<Dish> dishTableView;
    private TableView<Recipe> recipeTableView;
    private ServerInterface server;
    private ObservableList<Ingredient> ingredientObservableList;
    ComboBox<Ingredient> ingredientComboBox;
    private DishTab dishTab;


    public EditRecipeTab (String name, DishTab dishTab, ServerInterface server){
        super(name);
        this.server = server;
        this.dishTableView = dishTab.getDishTableView();
        this.recipeTableView = dishTab.getRecipeTableView();
        this.dishTab = dishTab;
        ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());

        VBox superbox = new VBox();
        HBox buttonbox = new HBox();

        ingredientComboBox = new ComboBox<>(ingredientObservableList);

        Button addIngredient = new Button("Add Ingredient");
        addIngredient.setOnAction(event -> addIngredientClicked());

        Button removeIngredient = new Button("Remove Ingredient");
        removeIngredient.setOnAction(event -> removeIngredientClicked());

        buttonbox.getChildren().addAll(addIngredient,removeIngredient);

        superbox.getChildren().addAll(ingredientComboBox, buttonbox);
        this.setContent(superbox);

    }

    private void removeIngredientClicked() {
        Recipe recipe = dishTab.getRecipeTableView().getSelectionModel().getSelectedItem();
        Dish dish = dishTab.getDishTableView().getSelectionModel().getSelectedItem();
        dish.getRecipe().remove(recipe.getIngredient(),recipe.getAmount());
        dishTab.displayRecipe(dish);


    }

    public void addIngredientClicked(){
        System.out.println("before add:");

        Dish dish = dishTableView.getSelectionModel().getSelectedItem();
        for (Map.Entry<Ingredient,Number> cursor: dish.getRecipe().entrySet()) {
            System.out.println(cursor.getKey().getName());
        }
        dish.getRecipe().put(ingredientComboBox.getValue(),1);
        dishTab.displayRecipe(dish);
        System.out.println("After Add");
        for (Map.Entry<Ingredient,Number> cursor: dish.getRecipe().entrySet()) {
            System.out.println(cursor.getKey().getName());
        }
    }
}
