package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

public class AddDishTab extends MainTab {
    ServerInterface server;
    private ObservableList<Dish> dishObservableList;
    private TableView<Dish> dishTableView;
    private TextField nameInput;
    private TextField descriptionInput;
    private TextField priceInput;
    private TextField restockTInput;
    private TextField restockAInput;
    private DishTab dishTab;

    public AddDishTab (String name, ServerInterface server, DishTab dishTab){
        super(name);
        this.server = server;
        this.dishObservableList = dishTab.getDishObservableList();
        this.dishTableView = dishTab.getDishTableView();
        VBox superPane = new VBox();
        nameInput = new TextField();
        descriptionInput = new TextField();
        priceInput = new TextField();
        restockTInput = new TextField();
        restockAInput = new TextField();


        Button addButton = new Button("Add Dish");
        addButton.setOnAction(event -> addButtonClicked());

        Button removeButton = new Button("Remove Dish");
        removeButton.setOnAction(event -> removeButtonClicked());

        Button displayRecipe = new Button("Display Recipe");
        displayRecipe.setOnAction(event -> displayRecipeClicked());



        superPane.getChildren().addAll(nameInput,descriptionInput,priceInput,restockAInput,restockTInput,addButton,removeButton, displayRecipe);
//        superPane.addRow(0, nameInput, descriptionInput, priceInput);
//        superPane.addRow(1, restockAInput, restockTInput);
//        superPane.addRow(2, addButton, removeButton);
        this.setContent(superPane);

    }

    public void addButtonClicked(){
        try {
            server.addDish(nameInput.getText(), descriptionInput.getText(), NumberFormat.getInstance().parse(priceInput.getText()), NumberFormat.getInstance().parse(restockTInput.getText()), NumberFormat.getInstance().parse(restockAInput.getText()));
            dishObservableList = FXCollections.observableArrayList(server.getDishes());
            dishTab.setDishTableView(dishObservableList);
            nameInput.clear();
            descriptionInput.clear();
            priceInput.clear();
            restockAInput.clear();
            restockTInput.clear();
        } catch (ParseException e){
            System.out.println("Was unable to parse the numbers entered. ");
        }
    }

    public void removeButtonClicked(){
        try{
            server.removeDish(dishTableView.getSelectionModel().getSelectedItem());
            dishObservableList = FXCollections.observableArrayList(server.getDishes());
            dishTab.setDishTableView(dishObservableList);
        }catch (ServerInterface.UnableToDeleteException e){
            System.out.println("was unable to remove that");
        }
    }

    public void displayRecipeClicked(){
        Dish dish = dishTableView.getSelectionModel().getSelectedItem();
        Map<Ingredient, Number> recipe = dish.getRecipe();
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        for(Map.Entry<Ingredient,Number> cursor: recipe.entrySet()){
                    Recipe newRecipe = new Recipe(cursor.getKey(), cursor.getValue());
                    recipeArrayList.add(newRecipe);

        }
        ObservableList<Recipe> recipeObservableList = FXCollections.observableArrayList(recipeArrayList);
        for (Recipe temp : recipeObservableList
             ) {
            System.out.println(temp.getIngredient().getName() + " " + temp.getAmount());

        }

        dishTab.setRecipeTableView(recipeObservableList);

    }
}
