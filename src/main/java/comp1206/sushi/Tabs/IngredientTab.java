package comp1206.sushi.Tabs;

import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class IngredientTab extends MainTab {


    private TableView<Ingredient> ingredientTableView;
    private ObservableList<Ingredient> ingredientObservableList;
    private ObservableList<Supplier> supplierObservableList;
    private AddIngredientTab addIngredientTab;
    private EditIngredient editIngredient;
    private ServerInterface server;
    private DishTab dishTab;

    public IngredientTab(String name, ServerInterface server, DishTab dishTab){
        super(name);
        this.server = server;
        this.dishTab = dishTab;

        ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
        supplierObservableList = FXCollections.observableArrayList(server.getSuppliers());

        HBox superBox = new HBox();
        superBox.setPadding(new Insets(10,10,10,10));
        superBox.setSpacing(10);




        ingredientTableView = new TableView<>();

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

        ingredientTableView.getColumns().addAll(nameColumn,unitColumn, supplierColumn, restockThresholdColumn, restockAmountColumn);
        ingredientTableView.setItems(ingredientObservableList);

        TabPane inputTabPane = new TabPane();
        editIngredient = new EditIngredient("Edit Ingredient",this, server);
        addIngredientTab = new AddIngredientTab("Add Ingredient", server, this, dishTab.getEditRecipeTab() );
        inputTabPane.getTabs().addAll(addIngredientTab, editIngredient);

        superBox.getChildren().addAll(ingredientTableView, inputTabPane);
        this.setContent(superBox);

    }

    public AddIngredientTab getAddIngredientTab() {
        return addIngredientTab;
    }

    public TableView<Ingredient> getIngredientTableView() {
        return ingredientTableView;
    }

    public void setItems(ObservableList<Ingredient> ingredientObservableList) {
        ingredientTableView.setItems(ingredientObservableList);
    }



    public ObservableList<Ingredient> getIngredientObservableList() {
        return ingredientObservableList;
    }

    public void setIngredientObservableList(ObservableList<Ingredient> ingredientObservableList) {
        this.ingredientObservableList = ingredientObservableList;
    }

    public ObservableList<Supplier> getSupplierObservableList() {
        return supplierObservableList;
    }

    public void setSupplierObservableList(ObservableList<Supplier> supplierObservableList) {
        this.supplierObservableList = supplierObservableList;
    }


}
