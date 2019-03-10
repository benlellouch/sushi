package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DishTab extends MainTab {

    private ServerInterface server;
    private TableView<Dish> dishTableView;
    private TableView<Ingredient> ingredientTableView;
    private ObservableList<Dish> dishObservableList;
    private ObservableList<Ingredient> ingredientObservableList;
    private ObservableList<Ingredient> recipeObservableList;
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
//        ingredientObservableList = FXCollections.observableList(server.getIngredients());

        HBox superBox = new HBox();

        VBox recipeAndInputBox = new VBox();
        ingredientTableView = new TableView<>();
        dishTableView = new TableView<>();

        // Table columns for Dish Table
        TableColumn<Dish,String> dishNameColumn = new TableColumn<>("Name");
        dishNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

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
        dishTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observableValue, Dish dish, Dish t1) {
                ingredientTableView.setItems(FXCollections.observableArrayList(server.getIngredients()));
            }
        });


        // Table columns for Ingredient Table
        TableColumn<Ingredient,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Ingredient,String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<Ingredient, Supplier> supplierColumn = new TableColumn<>("Supplier");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

        TableColumn<Ingredient, Number> restockThresholdColumn = new TableColumn<>("Restock Threshold");
        restockThresholdColumn.setCellValueFactory(new PropertyValueFactory<>("restockThreshold"));

        TableColumn<Ingredient, Number> restockAmountColumn = new TableColumn<>("Restock Amount");
        restockAmountColumn.setCellValueFactory(new PropertyValueFactory<>("restockAmount"));

        ingredientTableView.getColumns().addAll(nameColumn, unitColumn, supplierColumn,restockThresholdColumn,restockAmountColumn);
//        ingredientTableView.setItems(ingredientObservableList);

        // Setting up the Add new Dish tab and the edit recipe tab
        TabPane addAndEditTab = new TabPane();
        MainTab addTab = new AddDishTab("Add Dish", server, dishObservableList, dishTableView);
//        MainTab addTab = new MainTab("Add Placeholder");
//        MainTab addTab = createAddDishTab();
        Tab editTab = new Tab("Edit Placeholder");
        addAndEditTab.getTabs().addAll(addTab,editTab );

        recipeAndInputBox.getChildren().addAll(ingredientTableView, addAndEditTab);
        superBox.getChildren().addAll(dishTableView, recipeAndInputBox);
        this.setContent(superBox);


    }

    public ObservableList<Dish> getDishObservableList() {
        return dishObservableList;
    }

    public void setDishObservableList(ObservableList<Dish> dishObservableList) {
        this.dishObservableList = dishObservableList;
    }

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


}
