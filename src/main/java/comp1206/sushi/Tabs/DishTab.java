package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;

public class DishTab extends MainTab {

    private ServerInterface server;
    private TableView<Dish> dishTableView;
    private TableView<Recipe> recipeTableView;
    private ObservableList<Dish> dishObservableList;
    private ObservableList<Ingredient> ingredientObservableList;
    private ObservableList<Recipe> recipeObservableList;
    private ComboBox<Ingredient> ingredientComboBox;
    private TextField nameInput;
    private TextField descriptionInput;
    private TextField priceInput;
    private TextField restockTInput;
    private TextField restockAInput;

    public DishTab(String name, ServerInterface server){
        super(name);
        this.server = server;
        dishObservableList = FXCollections.observableArrayList(server.getDishes());
        ingredientObservableList = FXCollections.observableList(server.getIngredients());


        HBox superBox = new HBox();
        superBox.setPadding(new Insets(10,10,10,10));
        superBox.setSpacing(10);

        VBox recipeAndInputBox = new VBox();

        dishTableView = new TableView<>();

        recipeTableView = new TableView<>();
        recipeTableView.setPrefWidth(200);
        // Table columns for Ingredient Table
        TableColumn<Recipe,Ingredient> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        nameColumn.setPrefWidth(110);

        TableColumn<Recipe,Number> unitColumn = new TableColumn<>("Amount");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        unitColumn.setPrefWidth(110);

        recipeTableView.getColumns().addAll(nameColumn, unitColumn);
//        recipeTableView.setItems(ingredientObservableList);



        // Table columns for Dish Table
        TableColumn<Dish,String> dishNameColumn = new TableColumn<>("Name");
        dishNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn<Dish,String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Dish, Number> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Dish, Number> dishRestockThresholdColumn = new TableColumn<>("Restock Threshold");
        dishRestockThresholdColumn.setCellValueFactory(new PropertyValueFactory<>("restockThreshold"));

        TableColumn<Dish, Number> dishRestockAmountColumn = new TableColumn<>("Restock Amount");
        dishRestockAmountColumn.setCellValueFactory(new PropertyValueFactory<>("restockAmount"));

        dishTableView.getColumns().addAll(dishNameColumn, descriptionColumn,priceColumn,dishRestockThresholdColumn,dishRestockAmountColumn);
        dishTableView.setItems(dishObservableList);


        dishTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            Map<Ingredient,Number> recipe = newSelection.getRecipe();
//            for(Map.Entry<Ingredient,Number> cursor: recipe.entrySet()){
//                    Recipe newRecipe = new Recipe(cursor.getKey(), cursor.getValue());
//                    recipeObservableList.add(newRecipe);
//                }
//                recipeTableView.setItems(recipeObservableList);
//            Dish dish = dishTableView.getSelectionModel().getSelectedItem();
            displayRecipe(newSelection);

        });



//        dishTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dish>() {
//            @Override
//            public void changed(ObservableValue<? extends Dish> observableValue, Dish dish, Dish t1) {
//                Map<Ingredient,Number> recipe = observableValue.getValue().getRecipe();
//
//                for(Map.Entry<Ingredient,Number> cursor: recipe.entrySet()){
//                    ObservableList<Recipe> testObservableList = null;
//                    Recipe newRecipe = new Recipe(cursor.getKey(), cursor.getValue());
//                    testObservableList.add(newRecipe);
//                    recipeTableView.setItems(testObservableList);
//                }
//
//            }
//
//        });





        // Setting up the Add new Dish tab and the edit recipe tab
        TabPane addAndEditTab = new TabPane();
        MainTab addTab = new AddDishTab("Add Dish", server, this);
//        MainTab addTab = new MainTab("Add Placeholder");
//        MainTab addTab = createAddDishTab();
        MainTab editTab = new EditRecipeTab("Edit Recipe", this, server);
        addAndEditTab.getTabs().addAll(addTab,editTab );

        recipeAndInputBox.getChildren().addAll(recipeTableView, addAndEditTab);
        superBox.getChildren().addAll(dishTableView, recipeAndInputBox);
        this.setContent(superBox);


    }

    public ObservableList<Dish> getDishObservableList() {
        return dishObservableList;
    }



    public void setDishTableView(ObservableList<Dish> dishObservableList) {
        dishTableView.setItems(dishObservableList);
    }

    public void setRecipeObservableList(ObservableList<Recipe> recipeObservableList) {
        this.recipeObservableList = recipeObservableList;
    }

    public void setRecipeTableView(ObservableList<Recipe> recipeObservableList){
        recipeTableView.setItems(recipeObservableList);
    }

    public TableView<Recipe> getRecipeTableView() {
        return recipeTableView;
    }

    public ObservableList<Ingredient> getIngredientObservableList() {
        return ingredientObservableList;
    }
    public TableView<Dish> getDishTableView(){return dishTableView;}

    //    public MainTab createAddDishTab(){
//        MainTab addDishTab = new MainTab("Add Dish");
//        GridPane superPane = new GridPane();
//        nameInput = new TextField();
//        descriptionInput = new TextField();
//        priceInput = new TextField();
//        restockTInput = new TextField();
//        restockAInput = new TextField();
//        Button addButton = new Button("Add Dish");
//        Button removeButton = new Button("Remove Dish");
//        superPane.getChildren().addAll(nameInput,descriptionInput,priceInput,restockAInput,restockTInput,addButton,removeButton);
////        superPane.addRow(0, nameInput, descriptionInput, priceInput);
////        superPane.addRow(1, restockAInput, restockTInput);
////        superPane.addRow(2, addButton, removeButton);
//        addDishTab.setContent(superPane);
//        return addDishTab;
//    }
    public void printtest(){
        System.out.println("this is a test");
    }
    public void displayRecipe(Dish dish){
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

        setRecipeTableView(recipeObservableList);

    }


}
