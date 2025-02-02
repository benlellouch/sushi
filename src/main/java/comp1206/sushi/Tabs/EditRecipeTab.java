package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

public class EditRecipeTab extends MainTab{

    private TableView<Dish> dishTableView;
    private TableView<Recipe> recipeTableView;
    private ServerInterface server;
    private ObservableList<Ingredient> ingredientObservableList;
    ComboBox<Ingredient> ingredientComboBox;
    private TextField amount;
    private DishTab dishTab;


    public EditRecipeTab (String name, DishTab dishTab, ServerInterface server){
        super(name);
        this.server = server;
        this.dishTableView = dishTab.getDishTableView();
        this.recipeTableView = dishTab.getRecipeTableView();
        this.dishTab = dishTab;
        ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());

        VBox superbox = new VBox();
        superbox.setAlignment(Pos.TOP_CENTER);
        superbox.setPadding(new Insets(10,10,10,10));
        superbox.setSpacing(10);
        HBox buttonbox = new HBox();
        buttonbox.setAlignment(Pos.CENTER);
        buttonbox.setSpacing(10);

        ingredientComboBox = new ComboBox<>(ingredientObservableList);
        ingredientComboBox.setPromptText("Select ingredient");
        ingredientComboBox.setPrefWidth(200);


        amount = new TextField();
        amount.setPromptText("Enter an amount");
        amount.setPrefWidth(200);



        Button addIngredient = new Button("Add ");
        addIngredient.setPrefWidth(95);
        addIngredient.setOnAction(event -> addIngredientClicked());

        Button removeIngredient = new Button("Remove ");
        removeIngredient.setPrefWidth(95);
        removeIngredient.setOnAction(event -> removeIngredientClicked());



        buttonbox.getChildren().addAll(addIngredient,removeIngredient);

        superbox.getChildren().addAll(ingredientComboBox,amount, buttonbox);
        this.setContent(superbox);

    }

    private void removeIngredientClicked() {
        Recipe recipe = dishTab.getRecipeTableView().getSelectionModel().getSelectedItem();
        Dish dish = dishTab.getDishTableView().getSelectionModel().getSelectedItem();
        dish.getRecipe().remove(recipe.getIngredient(),recipe.getAmount());
        dishTab.displayRecipe(dish);


    }

    public void addIngredientClicked(){
        try {

            Ingredient ingredient = ingredientComboBox.getValue();
            if(ingredient != null) {
                System.out.println("before add:");

                Dish dish = dishTableView.getSelectionModel().getSelectedItem();
                for (Map.Entry<Ingredient, Number> cursor : dish.getRecipe().entrySet()) {
                    System.out.println(cursor.getKey().getName());
                }
                dish.getRecipe().put(ingredient, NumberFormat.getInstance().parse(amount.getText()));
                dishTab.displayRecipe(dish);
                System.out.println("After Add");
                for (Map.Entry<Ingredient, Number> cursor : dish.getRecipe().entrySet()) {
                    System.out.println(cursor.getKey().getName());
                }
            } else {comboBoxAlert(this);}
        }catch (ParseException e){
            System.out.println("Unable to parse");
        }
    }

    public void setIngredientComboBox(ObservableList<Ingredient> ingredientObservableList){
        ingredientComboBox.getItems().removeAll(this.ingredientObservableList);
        ingredientComboBox.setItems(ingredientObservableList);
    }
}
