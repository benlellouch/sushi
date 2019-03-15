package comp1206.sushi.Tabs;

import comp1206.sushi.common.Dish;
import comp1206.sushi.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;

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
        this.dishTab = dishTab;
//        this.dishObservableList = dishTab.getDishObservableList();
        this.dishTableView = dishTab.getDishTableView();

        VBox superPane = new VBox();
        HBox buttonBox = new HBox();
        nameInput = new TextField();
        descriptionInput = new TextField();
        priceInput = new TextField();
        restockTInput = new TextField();
        restockAInput = new TextField();


        Button addButton = new Button("Add Dish");
        addButton.setOnAction(event -> addButtonClicked());

        Button removeButton = new Button("Remove Dish");
        removeButton.setOnAction(event -> removeButtonClicked());

        Label nameLabel = new Label("Name of the dish: ");
        Label descriptionLabel = new Label("Description: ");
        Label priceLabel = new Label("Price: ");
        Label restockALabel = new Label("Restock Amount:");
        Label restockTLabel = new Label("Restock Threshold: ");




        buttonBox.getChildren().addAll(addButton,removeButton);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(10,10,10,10));
        buttonBox.setAlignment(Pos.CENTER);
        superPane.getChildren().addAll(nameLabel,nameInput,descriptionLabel,descriptionInput,priceLabel,priceInput,restockALabel,restockAInput,restockTLabel,restockTInput,buttonBox);
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

}
