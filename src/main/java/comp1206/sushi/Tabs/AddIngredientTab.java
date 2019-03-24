package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.common.Ingredient;
import comp1206.sushi.common.Supplier;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Map;

public class AddIngredientTab extends MainTab {
    private ComboBox<Supplier> supplierComboBox;
    private TextField nameInput;
    private TextField stringInput;
    private TextField restockTInput;
    private TextField restockAInput;
    private ServerInterface server;
    private IngredientTab ingredientTab;
    private ObservableList<Supplier> supplierObservableList;
    private EditRecipeTab editRecipeTab;
    public AddIngredientTab(String name, ServerInterface server, IngredientTab ingredientTab, EditRecipeTab editRecipeTab){
        super(name);
        this.server = server;
        this.ingredientTab = ingredientTab;
        this.editRecipeTab =editRecipeTab;
        supplierObservableList = ingredientTab.getSupplierObservableList();
        VBox inputBox = new VBox();
        inputBox.setSpacing(10);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        nameInput = new TextField();
        Label nameLabel = new Label("Name:");

        nameInput.setPromptText("Ex: Salmon");
        nameInput.setPrefWidth(200);

        stringInput = new TextField();
        Label unitLabel = new Label("Unit:");
        stringInput.setPromptText("Ex: kilograms");

        restockTInput = new TextField();
        Label restockTLabel = new Label("Restock Threshold:");
        restockTInput.setPromptText("Enter a Threshold");

        restockAInput = new TextField();
        Label restockALabel = new Label("Restock Amount:");
        restockAInput.setPromptText("Enter an Amount");

        Button addButton = new Button("Update");
        addButton.setPrefWidth(95);
        Button removeButton = new Button ("Remove");
        removeButton.setPrefWidth(95);
        buttonBox.getChildren().addAll(addButton,removeButton);

        supplierComboBox = new ComboBox<>(supplierObservableList);
        supplierComboBox.setPrefWidth(200);
        supplierComboBox.setPromptText("Select a supplier");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(event -> removeButtonClicked());

        inputBox.getChildren().addAll(nameLabel,nameInput,unitLabel, stringInput,restockTLabel, restockTInput, restockALabel,restockAInput, supplierComboBox, buttonBox);
        this.setContent(inputBox);

    }

    public void addButtonClicked(){
//        Postcode postcode = new Postcode(nameInput.getText());
        try {
            String name = nameInput.getText();
            String unit = stringInput.getText();
            Supplier supplier = supplierComboBox.getValue();
            Number restockAmount = NumberFormat.getInstance().parse(restockAInput.getText());
            Number restockThreshold = NumberFormat.getInstance().parse(restockTInput.getText());
            if(!name.equals("") && !unit.equals("")) {
                if (supplier!= null) {
                    server.addIngredient(nameInput.getText(), stringInput.getText(), supplierComboBox.getValue(), NumberFormat.getInstance().parse(restockTInput.getText()), NumberFormat.getInstance().parse(restockAInput.getText()));
//            ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
                    ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
//            ingredientTableView.setItems(ingredientObservableList);
                    ingredientTab.setItems(ingredientObservableList);
                    editRecipeTab.setIngredientComboBox(ingredientObservableList);
                    for (Ingredient temp : server.getIngredients()) {
                        System.out.println(temp.getName());
                    }

                    nameInput.clear();
                    stringInput.clear();
                    restockAInput.clear();
                    restockTInput.clear();
                    supplierComboBox.setValue(null);
                } else {
                    comboBoxAlert(this);
                }
            } else {unableToParse(this);}
        } catch (ParseException e){
            unableToParse(this);
        }
    }

    public void removeButtonClicked(){
//        ObservableList<Postcode> selectedProduct, allProduct;
//        allProduct = ingredientTableView.getItems();
//        selectedProduct= ingredientTableView.getSelectionModel().getSelectedItems();
//        selectedProduct.forEach(allProduct::remove);
        try{
            Ingredient ingredient = ingredientTab.getIngredientTableView().getSelectionModel().getSelectedItem();
            for (Dish temp : server.getDishes()){
                for (Map.Entry<Ingredient,Number> cursor : temp.getRecipe().entrySet()) {
                    if (ingredient == cursor.getKey()){
                        throw new ServerInterface.UnableToDeleteException("putain");
                    }
                }
            }
            server.removeIngredient(ingredient);
            ObservableList<Ingredient> ingredientObservableList = FXCollections.observableArrayList(server.getIngredients());
            ingredientTab.setItems(ingredientObservableList);
            editRecipeTab.setIngredientComboBox(ingredientObservableList);
            for (Ingredient temp: server.getIngredients()){
                System.out.println(temp.getName());
            }
        }catch(ServerInterface.UnableToDeleteException e){
            unableToDeleteAlert(this);
        }
    }

    public void setSupplierComboBox(ObservableList<Supplier> supplierObservableList){
        supplierComboBox.getItems().removeAll(this.supplierObservableList);
        supplierComboBox.getItems().addAll(supplierObservableList);
    }
}
