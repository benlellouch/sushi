package comp1206.sushi.Tabs;

import comp1206.sushi.server.ServerInterface;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddDishTab extends MainTab {
    ServerInterface server;
    private TextField nameInput;
    private TextField descriptionInput;
    private TextField priceInput;
    private TextField restockTInput;
    private TextField restockAInput;

    public AddDishTab (String name, ServerInterface server){
        super(name);
        this.server = server;
        GridPane superPane = new GridPane();
        nameInput = new TextField();
        descriptionInput = new TextField();
        priceInput = new TextField();
        restockTInput = new TextField();
        restockAInput = new TextField();
        Button addButton = new Button("Add Dish");
        Button removeButton = new Button("Remove Dish");
        superPane.getChildren().addAll(nameInput,descriptionInput,priceInput,restockAInput,restockTInput,addButton,removeButton);
        superPane.addRow(0, nameInput, descriptionInput, priceInput);
        superPane.addRow(1, restockAInput, restockTInput);
        superPane.addRow(2, addButton, removeButton);
        this.setContent(superPane);

    }
}
